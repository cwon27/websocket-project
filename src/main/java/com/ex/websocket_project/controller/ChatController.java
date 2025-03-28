package com.ex.websocket_project.controller;

import com.ex.websocket_project.dto.ChatMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// STOMP를 사용해 메세지를 처리하는 컨트롤러
@RestController
@RequiredArgsConstructor
public class ChatController {
    // 특정 사용자에게 메세지를 보내는데 사용되는 STOMP를 이용한 템플릿
    private final SimpMessagingTemplate template;

    // 공용 채팅방에 메세지 전송
    // 클라이언트에서 /app/chat.sendMsg 로 보냄
    @MessageMapping("/chat.sendMsg")
    public ChatMsg sendMsg(@RequestBody ChatMsg chatMsg){
        template.convertAndSend("/topic/message", chatMsg);

        return chatMsg;
    }
}
