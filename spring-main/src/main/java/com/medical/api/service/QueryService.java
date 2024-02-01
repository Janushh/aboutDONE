package com.medical.api.service;

import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;



public interface QueryService {
    ResponseEntity<byte[]> processAskQuestion(Integer username, String question) throws DocumentException;
    ResponseEntity<byte[]> processGenerateReportForUser(Integer userid) throws DocumentException;
    ResponseEntity<byte[]> getAllQueriesAsPdfResponse() throws DocumentException;
}
