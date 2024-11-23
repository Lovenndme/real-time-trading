package com.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.trade.constant.MessageConstant;
import com.trade.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        Result<Object> result = Result.builder()
                .msg(MessageConstant.INSUFFICIENT_PRIVILEGES)
                .code(401)
                .build();

        String json = JSON.toJSONString(result);

        try {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            log.error("json写出失败！");
        }
    }
}
