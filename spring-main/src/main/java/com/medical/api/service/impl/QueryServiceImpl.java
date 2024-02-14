package com.medical.api.service.impl;

import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;
import com.medical.api.entities.UserQuery;
import com.medical.api.exception.QueryException;
import com.medical.api.repository.UserQueryRepository;
import com.medical.api.service.OpenAIService;
import com.medical.api.service.PDFService;
import com.medical.api.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private static final String GPT_MODEL = "gpt-3.5-turbo-0613";

    private final UserQueryRepository userQueryRepository;

    private final OpenAIService openAIService;

    private final PDFService pdfService;

    @Override
    public byte[] processAskQuestion(Integer userid, String question) {
        ChatGptRequest request = new ChatGptRequest(GPT_MODEL, question);
        ChatGptResponse chatGptResponse = openAIService.sendRequest(request);

        String answer = chatGptResponse.getChoices().get(0).getMessage().getContent();
        UserQuery userQuery = new UserQuery();
        userQuery.setUserid(userid);
        userQuery.setQuestion(question);
        userQuery.setAnswer(answer);
        userQuery.setTimestamp(LocalDateTime.now());

        userQueryRepository.save(userQuery);

        return pdfService.generatePdfFromText(answer);
    }

    @Override
    public byte[] processGenerateReportForUser(Integer userid) {
        List<UserQuery> queries = userQueryRepository.findByUserid(userid);
        if (queries != null && !queries.isEmpty()) {


            return pdfService.generatePdfReportForUser(queries);
        } else {
            throw new QueryException();
        }

    }

    @Override
    public byte[] getAllQueriesAsPdfResponse() {
        pdfService.generatePdfReportForUser(userQueryRepository.findAll());

        return null;
    }

}
