package io.kanghyun.oauth2_prac.app;

import io.kanghyun.oauth2_prac.dao.MemberRepository;
import io.kanghyun.oauth2_prac.domain.Member;
import io.kanghyun.oauth2_prac.dto.MemberDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    // OAuth2 제공자로부터 유저 정보를 불러오는 메서드
    // OAuth2UserRequest 안에는 Access Token, Client 정보 등이 들어 있음
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 소셜 제공자로부터 사용자 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("userRequest: {}", userRequest);
        MemberDetails memberDetails = MemberDetailsFactory.memberDetails(provider, oAuth2User);
        Optional<Member> memberOptional = memberRepository.findByEmail(memberDetails.getEmail());

        Member findMember = memberOptional.orElseGet(
                () -> {
                    Member saved = Member.builder()
                            .name(memberDetails.getName())
                            .email(memberDetails.getEmail())
                            .provider(provider)
                            .build();
                    return memberRepository.save(saved);
                }
        );
        if (findMember.getProvider().equals(provider)) {
            return memberDetails.setRole(findMember.getRole());
        } else {
            // 이미 가입된 객체이므로 예외처리
            throw new RuntimeException();
        }
    }

}
