package io.github.StardewValley.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // قابلیت Message Broker را فعال می‌کند
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // setAllowedOrigins("*") را اضافه کنید
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // پیشوند آدرس‌هایی که پیام‌ها از کلاینت به سرور فرستاده می‌شوند.
        registry.setApplicationDestinationPrefixes("/app");

        // پیشوند "کانال‌"هایی که سرور پیام‌ها را برای کلاینت‌ها در آن پخش می‌کند.
        registry.enableSimpleBroker("/topic");
    }

}
