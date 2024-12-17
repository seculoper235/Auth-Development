package com.example.demo.service.auth;

import com.example.demo.domain.kakao.KakaoClient;
import com.example.demo.domain.kakao.KakaoUnlinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoService extends AdminKeyClientService {
    @Value("${oauth.kakao.adminKey}")
    private String adminKey;
    private final KakaoClient kakaoClient;

    public void unlink(String uid) {
        String contentType = MediaType.APPLICATION_JSON_VALUE;
        String authorization = "KakaoAK " + adminKey;
        KakaoUnlinkRequest unlinkRequest = new KakaoUnlinkRequest("user_id", uid);

        kakaoClient.unlink(contentType, authorization, unlinkRequest);
    }
}
