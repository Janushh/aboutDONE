package com.medical.api.service;

import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;

public interface QueryService {
    ResponseEntity<byte[]> processAskQuestion(String username, String question) throws DocumentException;
    ResponseEntity<byte[]> processGenerateReportForUser(String username) throws DocumentException;
}
