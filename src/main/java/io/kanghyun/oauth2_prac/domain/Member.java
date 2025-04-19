package io.kanghyun.oauth2_prac.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;
    private String role = "ROLE_USER";

    private String email;
    private String provider;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Member(String name, String nickname, String email, String provider) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }

}
