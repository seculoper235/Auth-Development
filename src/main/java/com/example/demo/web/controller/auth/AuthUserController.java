package com.example.demo.web.controller.auth;

import com.example.demo.model.common.auth.SnsAccount;
import com.example.demo.model.common.auth.SnsType;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.auth.SnsAccountInfo;
import com.example.demo.web.exception.model.AuthorizedClientException;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import com.example.demo.web.exception.model.DuplicatedEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/user/")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthService authService;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("{id}")
    public ResponseEntity<AuthUserInfo> find(@PathVariable String id) throws CredentialNotMatchException {
        AuthUserInfo result = authService.find(Long.parseLong(id));

        return ResponseEntity.ok(result);
    }

    @PostMapping("link/{type}")
    public ResponseEntity<?> link(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody SnsLoginRequest request,
            @PathVariable String type) throws DuplicatedEntityException, AuthorizedClientException {
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(type, request.uid());

        OAuth2RefreshToken refreshToken = Optional.ofNullable(authorizedClient)
                .flatMap(client -> Optional.ofNullable((client.getRefreshToken())))
                .orElseThrow(() -> new AuthorizedClientException(type + " Authorized Client Info is missing!"));

        SnsAccount param = SnsAccount.builder()
                .uid(request.uid())
                .type(SnsType.valueOf(type.toUpperCase()))
                .refreshToken(refreshToken.getTokenValue())
                .build();

        SnsAccountInfo snsAccount = authService.register(principal.getEmail(), param);

        String location = "/api/auth/user/" + principal.getName() + "/link/" + snsAccount.id();

        return ResponseEntity.created(URI.create(location))
                .build();
    }

    @DeleteMapping("link/{type}")
    public ResponseEntity<?> unlink(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody SnsLoginRequest request,
            @PathVariable String type) {

        SnsAccount param = SnsAccount.builder()
                .uid(request.uid())
                .type(SnsType.valueOf(type.toUpperCase()))
                .build();

        authService.deregister(principal.getEmail(), param);

        return ResponseEntity.noContent().build();
    }
}
