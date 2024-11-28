package com.example.demo.web.controller.auth;

import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.token.TokenInfo;
import com.example.demo.service.token.TokenService;
import com.example.demo.web.controller.user.SignupRequest;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import com.example.demo.web.exception.model.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthService authService;

    private final TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        AuthUser authUser = AuthUser.builder()
                .name(signupRequest.name())
                .email(signupRequest.email())
                .password(signupRequest.password())
                .build();

        AuthUserInfo userInfo = authService.register(authUser);

        return ResponseEntity.created(URI.create("/api/user/" + userInfo.id())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws CredentialNotMatchException {
        UserPrincipal principal = authService.authenticate(request.email(), request.password());
        TokenInfo tokenInfo = tokenService.createToken(principal);

        LoginResponse response = new LoginResponse(Long.parseLong(principal.getName()), principal.getEmail(), tokenInfo);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestBody String refreshToken) throws InvalidTokenException {
        String token = tokenService.reissueToken(refreshToken);
        ReissueResponse response = new ReissueResponse(token);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String refreshToken) {
        tokenService.deletePrincipal(refreshToken);

        return ResponseEntity.noContent().build();
    }
}
