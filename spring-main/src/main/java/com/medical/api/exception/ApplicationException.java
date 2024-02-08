package com.medical.api.exception;

public abstract class ApplicationException extends RuntimeException{

    protected ApplicationException() { super(); }

    protected ApplicationException(String message) {
        super(message);
    }

    protected ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ApplicationException(Throwable cause) {
        super(cause);
    }

    public String getMessageCode() {
        return this.getClass().getName();
    }
}
