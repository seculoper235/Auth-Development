package com.example.demo.common.http;

public interface ClientFactory {
    /** Client Bean 생성</br>
     * Target URL과 Rest API 요청 명세 인터페이스를 가지고 생성 */
    <T> T createClient(String baseUrl, Class<T> clientSpec);
}
