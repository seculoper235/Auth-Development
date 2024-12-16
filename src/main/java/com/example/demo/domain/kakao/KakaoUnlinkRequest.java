package com.example.demo.domain.kakao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record KakaoUnlinkRequest(
        String target_id_type,
        String target_id
) {
}
