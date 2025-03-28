package com.ex.websocket_project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// STOMP를 사용해 메세지 브로커를 설정
// 메세지 브로커의 설정을 정의하는 메서드들을 제공
// 이를 통해 메세지 브로커를 설정하고  STOMP 엔드포인트를 등록할 수 있음
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketStompBrokerConfig implements WebSocketMessageBrokerConfigurer {
    // 메세지 브로커를 구성하는 메서드
    // 메세지 브로커가 특정 목적지로 메세지를 라우팅 하는 방식을 설정함
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독(sub) : 접두사로 시작하는 메세지를 브로커가 처리하도록 처리
        // 클라이언트는 이 접두사로 시작하는 주제를 구독해 메세지를 받을 수 있음
        // 브로드캐스트
        registry.enableSimpleBroker("/topic");

        // 발행(pub) : 접두사로 시작하는 메세지는 @MessageMapping이 달린 메서드로 라우팅됨
        // 클라이언트가 서버로 메세지를 보낼 때 이 접두사를 사용함
        // 메세지 전송
        registry.setApplicationDestinationPrefixes("/app");
    }

    // STOMP 엔드포인트를 등록하는 메서드
    // 선택적으로 SockJS 폴백 옵션을 활성화하고 구성
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info("***** WebSocket STOMP 사용");
        // withSockJs : WebSocket 지원 X 브라우저도 WebSocket 기능을 사용할 수 있게 해줌
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*");
    }
}
