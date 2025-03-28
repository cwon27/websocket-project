package com.ex.websocket_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//클라이언트와 데이터를 주고받기 위한 객체
@Data
@AllArgsConstructor
public class ChatMsg {
    private String sender; // 보내는 주체
    private String content; // 메세지 내용
    private String regDt; // 시간
}
