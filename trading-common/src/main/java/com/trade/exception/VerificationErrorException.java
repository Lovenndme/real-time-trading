package com.trade.exception;

public class VerificationErrorException extends RuntimeException {
    public VerificationErrorException() {
        super();
    }

    public VerificationErrorException(String message) {
        super(message);
    }

    public VerificationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationErrorException(Throwable cause) {
        super(cause);
    }

    protected VerificationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
