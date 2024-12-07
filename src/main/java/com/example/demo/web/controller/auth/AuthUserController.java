package com.example.demo.web.controller.auth;

import com.example.demo.model.common.auth.SnsAccount;
import com.example.demo.model.common.auth.SnsType;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.auth.SnsAccountInfo;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import com.example.demo.web.exception.model.DuplicatedEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth/user/")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthService authService;

    @GetMapping("{id}")
    public ResponseEntity<AuthUserInfo> find(@PathVariable String id) throws CredentialNotMatchException {
        AuthUserInfo result = authService.find(Long.parseLong(id));

        return ResponseEntity.ok(result);
    }

    @PostMapping("link/{type}")
    public ResponseEntity<?> link(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody SnsLoginRequest request,
            @PathVariable String type) throws CredentialNotMatchException, DuplicatedEntityException {
        SnsAccount param = SnsAccount.builder()
                .uid(request.uid())
                .type(SnsType.valueOf(type.toUpperCase()))
                .build();

        SnsAccountInfo snsAccount = authService.register(principal.getEmail(), param);

        String location = "/api/auth/user/" + principal.getName() + "/link/" + snsAccount.id();

        return ResponseEntity.created(URI.create(location))
                .build();
    }
}
