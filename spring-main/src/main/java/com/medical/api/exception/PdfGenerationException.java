package com.medical.api.exception;

public class PdfGenerationException extends ApplicationException {
    public PdfGenerationException () {
        super("Error with pdf generation");
    }
}