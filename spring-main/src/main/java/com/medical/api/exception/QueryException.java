package com.medical.api.exception;

public class QueryException extends ApplicationException {

    public QueryException() {
        super("No queries found for user with ID: ");
    }
}