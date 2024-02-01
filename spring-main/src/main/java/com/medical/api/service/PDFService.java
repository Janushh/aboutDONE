package com.medical.api.service;

import com.itextpdf.text.DocumentException;
import com.medical.api.entities.UserQuery;
import java.util.List;

public interface PDFService {
    byte[] generatePdfFromPrompt(String prompt) throws DocumentException;
    byte[] generatePdfFromText(String text) throws DocumentException;
    byte[] generatePdfReportForUser(List<UserQuery> queries) throws DocumentException;

}
