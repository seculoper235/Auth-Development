package com.example.demo.common.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class RestClientFactory implements ClientFactory {

    protected HttpServiceProxyFactory httpServiceProxyFactory(String baseUrl) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();

        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Override
    public <T> T createClient(String baseUrl, Class<T> clientSpec) {
        return httpServiceProxyFactory(baseUrl)
                .createClient(clientSpec);
    }
}
