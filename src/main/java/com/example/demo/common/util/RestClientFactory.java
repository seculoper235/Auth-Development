package com.example.demo.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/* Common/Utils */
// 프로젝트 전반에서 사용되는 Util 객체
@RequiredArgsConstructor
public abstract class RestClientFactory {
    private final String url;

    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(url)
                .build();
    }

    // Eternal Adapter를 생성하는 Factory
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient()))
                .build();
    }
}
