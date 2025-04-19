package io.kanghyun.oauth2_prac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/sign-up")
                            .anonymous()
                            .requestMatchers("/user/**")
                            .hasAnyAuthority("ROLE_USER")
                            .requestMatchers("/admin/**")
                            .hasAnyAuthority("ROLE_ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .build();

    }
}