package com.trade.exception;

public class GenerateQRCodeFailureException extends RuntimeException {
    public GenerateQRCodeFailureException() {
        super();
    }

    public GenerateQRCodeFailureException(String message) {
        super(message);
    }

    public GenerateQRCodeFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateQRCodeFailureException(Throwable cause) {
        super(cause);
    }

    protected GenerateQRCodeFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
