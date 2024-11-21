package com.example.demo.web.controller.auth;

import com.example.demo.model.common.security.TokenInfo;
import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.service.SecurityService;
import com.example.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class SecurityController {
    private final SecurityService securityService;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UserPrincipal principal = securityService.authenticate(request.email(), request.password());
        TokenInfo tokenInfo = tokenService.createToken(principal);

        LoginResponse response = new LoginResponse(Long.parseLong(principal.getName()), principal.getEmail(), tokenInfo);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestBody String refreshToken) {
        String token = tokenService.reissueToken(refreshToken);
        ReissueResponse response = new ReissueResponse(token);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String refreshToken) {
        tokenService.deleteUserPrincipal(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
