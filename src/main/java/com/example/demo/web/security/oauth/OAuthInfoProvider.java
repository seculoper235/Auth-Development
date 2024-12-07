package com.example.demo.web.security.oauth;

import com.example.demo.model.common.auth.SnsType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuthInfoProvider {
    public static OAuthInfo getOAuthInfo(SnsType snsType, OAuth2User oAuth2User) {
        return switch (snsType) {
            case GOOGLE -> new GoogleOAuthInfo(oAuth2User.getAttributes());
            case NAVER -> new NaverOAuthInfo(oAuth2User.getAttribute("response"));
            case KAKAO -> new KakaoOAuthInfo(oAuth2User.getAttributes());
        };
    }
}
