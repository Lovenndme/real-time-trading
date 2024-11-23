package com.trade.exception;

public class AlreadyExistException extends BaseException{
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
