package com.example.demo.web.controller.user;

import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.auth.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class AuthUserController {
    private final AuthUserService authUserService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        AuthUser authUser = AuthUser.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();

        AuthUserInfo userInfo = authUserService.register(authUser);

        return ResponseEntity.created(URI.create("/api/user/" + userInfo.id())).build();
    }
}
