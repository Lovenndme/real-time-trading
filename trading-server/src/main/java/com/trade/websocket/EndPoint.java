package com.trade.websocket;

import com.trade.constant.MessageConstant;
import com.trade.context.WebSocketContext;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket")
@Component
@Slf4j
public class EndPoint {

    private static final Map<Long, Session> onlineUsers = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        Long userId = WebSocketContext.getCurrentId();
        if (userId != null) {
            // 将用户信息存入 WebSocket Session 的属性中
            session.getUserProperties().put("userId", userId);
            onlineUsers.put(userId, session);
            log.info("用户 {} 已连接", userId);
        } else {
            log.warn("未能找到用户ID，连接未加入在线用户列表");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Long userId = (Long) session.getUserProperties().get("userId");
        if (userId == null) {
            log.warn("未找到 session 中的用户ID，无法处理消息");
        }
    }

    @OnClose
    public void onClose(Session session) {
        Long userId = (Long) session.getUserProperties().get("userId");
        if (userId != null) {
            onlineUsers.remove(userId);
            log.info("用户 {} 已下线", userId);
        } else {
            log.warn("用户ID为 null，未能成功移除连接");
        }
    }

    public void sendWarningToAdmin(String fundType, List<Long> ids) {
        for (Long id : ids) {
            Session session = onlineUsers.get(id);
            if (session == null) {
                log.warn("当前管理用户ID：{}不在线",id);
                continue;
            }
            try {
                session.getBasicRemote().sendText(MessageConstant.FUND_POOL_LACK + fundType);
            } catch (IOException e) {
                log.error(MessageConstant.MESSAGE_SEND_FAILURE);
            }
        }
    }
}