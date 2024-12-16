package com.example.demo.common.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class RestClientFactory implements ClientFactory {

    protected HttpServiceProxyFactory httpServiceProxyFactory(String baseUrl) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl)
                .defaultStatusHandler(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                throw new RuntimeException("Client exception");
                            }
                            if (response.getStatusCode().is5xxServerError()) {
                                throw new RuntimeException("Server exception");
                            }
                            throw new RestClientException("Unexpected response status: " + response.getStatusCode());
                        }
                ).build();

        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Override
    public <T> T createClient(String baseUrl, Class<T> clientSpec) {
        return httpServiceProxyFactory(baseUrl)
                .createClient(clientSpec);
    }
}
