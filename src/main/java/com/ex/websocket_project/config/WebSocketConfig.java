package com.ex.websocket_project.config;

import com.ex.websocket_project.config.handler.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
    WebSocket 최초 연결을 위해 구성하는 환경 구성 파일 클래스
    - @EnableWebSocket : WebSocket을 활성화
*/
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatWebSocketHandler chatWebSocketHandler;

    // WebSocketConfigurer 인터페이스로부터 registerWebSocketHandlers 메서드 Override
    // -> 이 메서드를 통해 WebSocket 핸들러를 등록할 수 있음
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("***** 최초 WebSocket 연결을 위한 등록 Handler");

        registry.addHandler(chatWebSocketHandler, "ws-ex").setAllowedOriginPatterns("*");
    }
}