package com.medical.api.exception;

public class EmptyResponseException extends ApplicationException{
    public EmptyResponseException() {
        super("Failed to generate response from OpenAI Service");
    }
}