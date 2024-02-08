package com.medical.api.error_handling;

import com.medical.api.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        String errorDetails = ex.getLocalizedMessage();
        if (errorDetails == null) errorDetails = ex.toString();
        return handleExceptionInternal(ex, errorDetails,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An error occurred: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}

//1. Usunąć throw w kontrolerach
//2. Usunąć Response Entities w Service
//3. Napisać ApplicationException, EmptyResponseException, PdfGenerationEXception
//4. DRY - do not repeat yourself
