package io.kanghyun.oauth2_prac.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(chain = true)
public class MemberDetails implements OAuth2User {

    private String name;
    private String email;
    // 사용자의 속성정보 담을 변수
    private Map<String, Object> attributes;

    @Setter
    private String role;

    @Builder
    public MemberDetails(String name, String email, Map<String, Object> attributes, String role) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
        this.role = role;
    }

    // 유저 권한 정보 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}