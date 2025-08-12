package io.github.StardewValley.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.shared.dto.ChatMessageDTO;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatService {

    private StompSession stompSession;
    private WebSocketStompClient stompClient;
    private volatile boolean isConnected = false;
    public final List<ChatMessageDTO> messages = Collections.synchronizedList(new ArrayList<>());

    public boolean isConnected() {
        return this.isConnected;
    }

    public void connect(String serverIp, int serverPort) {
        // برای اتصال مستقیم، از پروتکل ws:// استفاده می‌کنیم
        String url = "ws://" + serverIp + ":" + serverPort + "/ws";
        System.out.println("1. CHAT_SERVICE: Attempting to connect to URL: " + url);

        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        System.out.println("2. CHAT_SERVICE: StompClient created. Calling connect()...");

        try {
            this.stompClient.connect(url, new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    System.out.println("3. CHAT_SERVICE: SUCCESS! afterConnected callback was executed.");
                    stompSession = session;
                    isConnected = true;
                    subscribeToPublicTopic();
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                    System.err.println("4. CHAT_SERVICE: ERROR! WebSocket transport error occurred.");
                    exception.printStackTrace();
                    isConnected = false;
                }

                @Override
                public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
                    System.err.println("5. CHAT_SERVICE: ERROR! STOMP protocol error.");
                    ex.printStackTrace();
                    isConnected = false;
                }
            });
        } catch (Exception e) {
            System.err.println("An unexpected exception occurred during the connect call itself.");
            e.printStackTrace();
        }
    }

    private void subscribeToPublicTopic() {
        if (!isConnected) {
            System.err.println("Cannot subscribe, not connected.");
            return;
        }
        System.out.println("3.1. CHAT_SERVICE: Attempting to subscribe to /topic/public");
        stompSession.subscribe("/topic/public", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessageDTO.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Message received on topic!");
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
        if (stompClient != null) {
            stompClient.stop();
        }
        isConnected = false;
        System.out.println("Chat service disconnected.");
    }
}
