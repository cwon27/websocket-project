package com.ex.websocket_project.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// WebSocket 연결 이후 연결을 처리하는 핸들러
// TextWebSocketHandler를 상속받아 최초 소켓 세션을 연결하고 소켓 전송 오류 발생시 텍스트 기반 메세지를 보내거나 받을 수 있도록 처리
// 텍스트 기반의 WebSocket 메시지를 처리를 수행
@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // WebSocket Session 관리 리스트
    private static final Map<String, WebSocketSession> clientSession = new ConcurrentHashMap<>();

    // 연결 성공
    // WebSocket 연결이 성공적으로 완료되고 사용할 준비가 된 후 호출됨
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("***** WebSocket 연결 완료됨. 연결 session : {}", session.getId());
        // 성공시 session 값 추가
        clientSession.put(session.getId(), session);
    }

    // 메세지 전달
    // 새로운 WebSocket 메세지가 도착했을 때 실행됨
    // 전달받은 메세지를 순회하며 메세지를 전송함
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.info("***** WebSocket 통해 메세지 수신 : {}", textMessage.getPayload());

        // session이 닫혀있으면 메세지 보낼 수 없음
        if(!session.isOpen()){
            log.info("****** 세션이 이미 닫힘 : {}",session.getId());
            return;
        }

        clientSession.forEach((key, value) -> {
            log.info("***** key : {}, value : {}", key, value);

            if (!key.equals(session.getId())) {
                // 같은 아이디가 아니면 메세지 전달 -> 자기가 보낸게 아니니까
                try {
                    value.sendMessage(textMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 소켓 종료 및 전송 오류
    // WebSocket 연결이 한쪽으로부터 종료되거나 전송 오류가 발생한 경우 실행
    // 종료 및 오류시 해당 세션 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        clientSession.remove(session);
        log.info("***** WebSocket 연결 종료됨. 종료 session : {} , 종료 상태 : {}", session.getId(), closeStatus);
    }
}
