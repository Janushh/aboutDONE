package com.medical.api.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;
import com.medical.api.entities.UserQuery;
import com.medical.api.exception.EmptyResponseException;
import com.medical.api.exception.PdfGenerationException;
import com.medical.api.service.OpenAIService;
import com.medical.api.service.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService {

    private final OpenAIService openAIService;
    private static final String GPT_MODEL = "gpt-3.5-turbo-0613";

    @Override
    public byte[] generatePdfFromPrompt(String prompt) {
        ChatGptRequest request = new ChatGptRequest(GPT_MODEL, prompt);
        ChatGptResponse chatGptResponse = openAIService.sendRequest(request);

        if (chatGptResponse == null || chatGptResponse.getChoices().isEmpty()) {
            throw new PdfGenerationException();
        }

        return generatePdfFromText(chatGptResponse.getChoices().get(0).getMessage().getContent());
    }

    @Override
    public byte[] generatePdfFromText(String text) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph(text));
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            throw new PdfGenerationException();
        }
    }

    @Override
    public byte[] generatePdfReportForUser(List<UserQuery> queries) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (UserQuery query : queries) {
                document.add(new Paragraph("Question: " + query.getQuestion()));
                document.add(new Paragraph("Answer: " + query.getAnswer()));
                document.add(new Paragraph("Time: " + query.getTimestamp().toString()));
                document.add(Chunk.NEWLINE);
            }
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            throw new PdfGenerationException();
        }

    }

}



