package io.github.StardewValley.server.controller;

import io.github.StardewValley.shared.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller // توجه کنید که @RestController نیست
public class ChatController {

    /**
     * این متد به پیام‌هایی که به آدرس /app/chat.sendMessage ارسال می‌شوند گوش می‌دهد.
     */
    @MessageMapping("/chat.sendMessage")
    /**
     * نتیجه این متد به تمام کلاینت‌هایی که به کانال /topic/public گوش می‌دهند، ارسال می‌شود.
     */
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessage) {
        // می‌توانید اینجا منطق بیشتری اضافه کنید، مثلا ذخیره پیام در دیتابیس
        chatMessage.setTimestamp(System.currentTimeMillis());
        return chatMessage;
    }
}
