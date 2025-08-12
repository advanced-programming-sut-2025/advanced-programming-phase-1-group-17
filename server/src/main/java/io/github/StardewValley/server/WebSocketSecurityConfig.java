package io.github.StardewValley.server; // یا پکیج config شما

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1) // <<-- این خط به این کانفیگ اولویت بالاتری می‌دهد
public class WebSocketSecurityConfig {

    @Bean
    // نام متد را تغییر می‌دهیم تا با Bean قبلی تداخل نداشته باشد
    public SecurityFilterChain webSocketSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // این زنجیره قوانین فقط برای آدرس‌هایی که با /ws/ شروع می‌شوند اعمال می‌شود
            .securityMatcher("/ws/**")
            .authorizeHttpRequests(auth -> auth
                // به تمام درخواست‌هایی که با الگوی بالا مطابقت دارند، اجازه بده
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
