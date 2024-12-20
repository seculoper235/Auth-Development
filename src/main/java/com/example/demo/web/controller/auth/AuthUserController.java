package com.example.demo.web.controller.auth;

import com.example.demo.model.common.auth.SnsAccount;
import com.example.demo.model.common.auth.SnsType;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.auth.SnsAccountInfo;
import com.example.demo.web.exception.model.AuthorizedClientException;
import com.example.demo.web.exception.model.DuplicatedEntityException;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth/user/")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthService authService;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("{id}")
    public ResponseEntity<AuthUserInfo> find(@PathVariable String id) {
        AuthUserInfo result = authService.find(Long.parseLong(id))
                .getOrElseThrow(it -> it);

        return ResponseEntity.ok(result);
    }

    @PostMapping("link/{type}")
    public ResponseEntity<?> link(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody SnsLoginRequest request,
            @PathVariable String type) throws DuplicatedEntityException, AuthorizedClientException {
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(type, request.uid());

        OAuth2RefreshToken refreshToken = Option.of(authorizedClient)
                .flatMap(client -> Option.of((client.getRefreshToken())))
                .getOrElseThrow(() -> new AuthorizedClientException(type + " Authorized Client Info is missing!"));

        SnsAccount param = SnsAccount.builder()
                .uid(authorizedClient.getPrincipalName())
                .type(SnsType.valueOf(authorizedClient.getClientRegistration().getRegistrationId()))
                .refreshToken(refreshToken.getTokenValue())
                .build();

        SnsAccountInfo snsAccount = authService.register(principal.getEmail(), param)
                .getOrElseThrow(it -> it);

        String location = "/api/auth/user/" + principal.getName() + "/link/" + snsAccount.id();

        return ResponseEntity.created(URI.create(location))
                .build();
    }

    @DeleteMapping("link/{type}")
    public ResponseEntity<?> unlink(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam String uid) {

        SnsAccount snsAccount = authService.findSnsAccount(uid)
                .getOrElseThrow(it -> it);

        authService.deregister(principal.getEmail(), snsAccount);

        return ResponseEntity.noContent().build();
    }
}
