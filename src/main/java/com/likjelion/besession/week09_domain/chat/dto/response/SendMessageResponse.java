package com.likjelion.besession.week09_domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMessageResponse {
    private ChatMessageResponse userMessage;
    private ChatMessageResponse aiMessage;
}
