package io.github.StardewValley.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.shared.dto.ChatMessageDTO;
import io.reactivex.disposables.CompositeDisposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatService {
    private StompClient stompClient;
    private CompositeDisposable compositeDisposable;
    private final ObjectMapper mapper = new ObjectMapper();
    public final List<ChatMessageDTO> messages = Collections.synchronizedList(new ArrayList<>());

    public void connect(String serverIp, int serverPort) {
        // برای سازگاری با SockJS سرور، از آدرس http استفاده می‌کنیم
        String url = "ws://" + serverIp + ":" + serverPort + "/ws/websocket";

        System.out.println("1. CHAT_SERVICE: Attempting to connect via OKHTTP to: " + url);

        // به صراحت می‌گوییم که از موتور OKHTTP استفاده کن
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

        stompClient.connect();
        compositeDisposable = new CompositeDisposable();

        // به وضعیت چرخه حیات اتصال گوش می‌دهیم
        compositeDisposable.add(stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("2. CHAT_SERVICE: SUCCESS! Stomp connection opened!");
                    subscribeToPublicTopic();
                    break;
                case ERROR:
                    System.err.println("3. CHAT_SERVICE: ERROR! Stomp connection error.");
                    lifecycleEvent.getException().printStackTrace();
                    break;
                case CLOSED:
                    System.out.println("4. CHAT_SERVICE: Stomp connection closed.");
                    break;
            }
        }));
    }

    private void subscribeToPublicTopic() {
        if (stompClient == null) return;

        System.out.println("2.1. CHAT_SERVICE: Subscribing to /topic/public...");
        compositeDisposable.add(stompClient.topic("/topic/public").subscribe(stompMessage -> {
            try {
                ChatMessageDTO message = mapper.readValue(stompMessage.getPayload(), ChatMessageDTO.class);
                messages.add(message);
                System.out.println("New message received: " + message.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, throwable -> {
            System.err.println("Error on topic subscription!");
            throwable.printStackTrace();
        }));
    }

    public void sendMessage(String username, String content) {
        if (content == null || content.trim().isEmpty() || stompClient == null) {
            System.err.println("Cannot send message. Client is null.");
            return;
        }

        if (!stompClient.isConnected()) {
            System.err.println("Cannot send message. Not connected.");
            return;
        }

        ChatMessageDTO chatMessage = new ChatMessageDTO();
        chatMessage.setSenderUsername(username);
        chatMessage.setContent(content);

        try {
            String jsonMessage = mapper.writeValueAsString(chatMessage);
            compositeDisposable.add(stompClient.send("/app/chat.sendMessage", jsonMessage).subscribe());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
