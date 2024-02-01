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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<byte[]> processAskQuestion(Integer userid, String question) throws DocumentException {
        ChatGptRequest request = new ChatGptRequest(GPT_MODEL, question);
        ChatGptResponse chatGptResponse = openAIService.sendRequest(request);

        if (chatGptResponse == null) {
            throw new RuntimeException("Response form OpenAI Service is null");
        }
        String answer = chatGptResponse.getChoices().get(0).getMessage().getContent();
        UserQuery userQuery = new UserQuery();
        userQuery.setUserid(userid);
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
    public ResponseEntity<byte[]> processGenerateReportForUser(Integer userid) throws DocumentException {
        List<UserQuery> queries = userQueryRepository.findByUserid(userid);
        byte[] pdfContent = pdfService.generatePdfReportForUser(queries);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "user_report_" + userid + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfContent);
    }
    @Override
    public ResponseEntity<byte[]> getAllQueriesAsPdfResponse() {
        try {
            byte[] pdfContent = pdfService.generatePdfReportForUser(userQueryRepository.findAll());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "all_queries.pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

