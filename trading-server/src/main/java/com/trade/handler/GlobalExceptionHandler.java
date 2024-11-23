package com.trade.handler;

import com.trade.constant.MessageConstant;
import com.trade.exception.BaseException;
import com.trade.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /*
    全局异常处理器
     */
    @ExceptionHandler(BaseException.class)
    public Result<Object> exceptionHandler(BaseException ex) {
        log.error("异常信息:{}", ex.getMessage());
        return Result.builder()
                .code(500)
                .msg(ex.getMessage())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    public Result<Object> exceptionHandler(NullPointerException ex) {
        log.error("空指针异常:{}", ex.getMessage());
        return Result.builder()
                .code(500)
                .msg(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> exceptionHandler(IllegalArgumentException ex) {
        log.error("参数异常:{}", ex.getMessage());
        return Result.builder()
                .code(500)
                .msg(ex.getMessage())
                .build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<Object> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.builder()
                    .code(500)
                    .msg(msg)
                    .build();
        }
        return Result.builder()
                .code(500)
                .msg(MessageConstant.UNKNOWN_ERROR)
                .build();
    }
}
