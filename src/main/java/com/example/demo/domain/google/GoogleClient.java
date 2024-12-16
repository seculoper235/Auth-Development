package com.example.demo.domain.google;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface GoogleClient {

    @PostExchange(url = "/revoke", contentType = "application/x-www-form-urlencoded")
    void unlink(
            @RequestParam String token);
}
