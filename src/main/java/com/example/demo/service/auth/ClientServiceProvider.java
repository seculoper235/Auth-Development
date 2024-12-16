package com.example.demo.service.auth;

import com.example.demo.domain.google.GoogleClient;
import com.example.demo.domain.kakao.KakaoClient;
import com.example.demo.domain.naver.NaverClient;
import com.example.demo.model.common.auth.SnsType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientServiceProvider {
    private final ClientRegistrationRepository registrationRepository;
    private final GoogleClient googleClient;
    private final NaverClient naverClient;
    private final KakaoClient kakaoClient;

    public ClientService getClientService(SnsType snsType) {
        return switch (snsType) {
            case GOOGLE -> new GoogleService(googleClient);
            case NAVER ->
                    new NaverService(registrationRepository.findByRegistrationId("naver"), naverClient);
            case KAKAO -> new KakaoService(kakaoClient);
        };
    }
}
