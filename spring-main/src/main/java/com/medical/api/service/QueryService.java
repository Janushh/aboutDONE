package com.medical.api.service;


public interface QueryService {
    byte[] processAskQuestion(Integer username, String question);
    byte[] processGenerateReportForUser(Integer userid);
    byte[] getAllQueriesAsPdfResponse();
}