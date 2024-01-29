package com.medical.api.service.impl;

import com.itextpdf.text.DocumentException;
import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;
import com.medical.api.entities.UserQuery;
import com.medical.api.repository.UserQueryRepository;
import com.medical.api.service.OpenAIService;
import com.medical.api.service.PDFService;
import com.medical.api.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class QueryServiceImpl implements QueryService {

    private static final String GPT_MODEL = "gpt-3.5-turbo-0613";

    private final UserQueryRepository userQueryRepository;

    private final OpenAIService openAIService;

    private final PDFService pdfService;

    @Override
    public ResponseEntity<byte[]> processAskQuestion(String username, String question) throws DocumentException {
        ChatGptRequest request = new ChatGptRequest(GPT_MODEL, question);
        ChatGptResponse chatGptResponse = openAIService.sendRequest(request);

        if (chatGptResponse == null) {
            throw new RuntimeException("Response form OpenAI Service is null");
        }
        String answer = chatGptResponse.getChoices().get(0).getMessage().getContent();
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(username);
        userQuery.setQuestion(question);
        userQuery.setAnswer(answer);
        userQuery.setTimestamp(LocalDateTime.now());
        userQueryRepository.save(userQuery);

        byte[] pdfContent = pdfService.generatePdfFromText(answer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "answer.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfContent);
    }

    @Override
    public ResponseEntity<byte[]> processGenerateReportForUser(String username) throws DocumentException {
        List<UserQuery> queries = userQueryRepository.findByUsername(username);
        byte[] pdfContent = pdfService.generatePdfReportForUser(queries);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "user_report_" + username + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfContent);
    }

}

