package com.medical.api.controller;

import com.medical.api.service.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PDFController {

    private final PDFService pdfService;

    @GetMapping("/generatePDF")
    public ResponseEntity<byte[]> generatePDF(@RequestParam("prompt") String prompt) {
        byte[] pdfContent = pdfService.generatePdfFromPrompt(prompt);

        if (pdfContent.length == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pdfContent);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "chat-response.pdf");

        return ResponseEntity.ok(pdfContent);
    }
}

