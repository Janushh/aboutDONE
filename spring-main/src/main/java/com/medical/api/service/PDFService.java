package com.medical.api.service;

import com.medical.api.entities.UserQuery;

import java.util.List;

public interface PDFService {
    byte[] generatePdfFromPrompt(String prompt);
    byte[] generatePdfFromText(String text);
    byte[] generatePdfReportForUser(List<UserQuery> queries);

}