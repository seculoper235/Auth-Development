package com.example.demo.service.auth;

import com.example.demo.domain.kakao.KakaoClient;
import com.example.demo.domain.kakao.KakaoUnlinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class KakaoService extends AdminKeyClientService {
    @Value("${oauth.kakao.adminKey}")
    private String adminKey;
    private final KakaoClient kakaoClient;

    public void unlink(String uid) {
        KakaoUnlinkRequest unlinkRequest = new KakaoUnlinkRequest("user_id", uid);

        kakaoClient.unlink(adminKey, unlinkRequest);
    }
}
