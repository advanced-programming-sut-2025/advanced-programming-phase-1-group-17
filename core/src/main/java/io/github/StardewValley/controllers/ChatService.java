package io.github.StardewValley.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.shared.dto.ChatMessageDTO;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatService {
    private StompSession stompSession;
    private final ObjectMapper mapper = new ObjectMapper();
    private volatile boolean isConnected = false; // برای چک کردن وضعیت اتصال

    public final List<ChatMessageDTO> messages = Collections.synchronizedList(new ArrayList<>());

    public void connect(String serverIp, int serverPort) {
        String url = "http://" + serverIp + ":" + serverPort + "/ws"; // <<-- تغییر کلیدی اینجاست
        // استفاده از SockJsClient برای سازگاری بهتر
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // دیگر از .get() استفاده نمی‌کنیم!
        stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("SUCCESS: Connected to WebSocket server!");
                stompSession = session;
                isConnected = true;
                subscribeToPublicTopic();
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.err.println("ERROR: WebSocket transport error!");
                exception.printStackTrace();
                isConnected = false;
            }
        });
    }

    private void subscribeToPublicTopic() {
        if (!isConnected) return;
        stompSession.subscribe("/topic/public", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessageDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messages.add((ChatMessageDTO) payload);
            }
        });
    }

    public void sendMessage(String username, String content) {
        if (content == null || content.trim().isEmpty()) return;

        if (!isConnected) {
            System.err.println("Cannot send message. Not connected.");
            return;
        }

        ChatMessageDTO chatMessage = new ChatMessageDTO();
        chatMessage.setSenderUsername(username);
        chatMessage.setContent(content);
        stompSession.send("/app/chat.sendMessage", chatMessage);
    }

    public void disconnect() {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.disconnect();
        }
        isConnected = false;
    }
}
