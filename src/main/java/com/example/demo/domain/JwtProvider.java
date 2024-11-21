package com.example.demo.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.common.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final String secret;

    private final JWTVerifier jwtVerifier;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;

        Algorithm algorithm = Algorithm.HMAC256(secret);

        this.jwtVerifier = JWT
                .require(algorithm)
                .withIssuer("dev StoryTeller")
                .build();
    }

    // 명시되어 있기는 하나 정해지진 않고 자유로운 사용을 권하고 있기에, 어플리케이션마다, JWT 사용 목적마다 다 다르다

    // ISS(Issuer): 발급해주는 곳. from의 의미를 담고 있다. 발급해주는 사이트 주소를 주로 사용한다
    // AUD(Audience): 발급받는 대상. to의 의미를 가지며, 이는 사람이 될 수도, 특정 Resource(URL)이 될 수도 있다
    // SUB(Subject): 발급 목적. 뭘 위해 발급하는지를 말하며, 명시에 따르면, JWT 자체는 해당 Sub 클레임에 대한 설명으로 이뤄져야 한다
    // 즉, 직역 그대로 제목/주제에 해당한다. for의 의미를 가진다
    // local/global 상으로 식별 가능해야 하기에, For User라는 의미로 주로 사용자 ID를 사용하는 편이다
    public String createToken(Long duration) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + duration);

        return JWT.create()
                .withIssuer("dev StoryTeller")
                .withSubject("dev StoryTeller")
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC256(secret));
    }

    public String createToken(UserPrincipal principal, Long duration) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + duration);

        return JWT.create()
                .withIssuer("dev StoryTeller")
                .withSubject("dev StoryTeller")
                .withAudience(principal.getName())
                .withClaim("id", principal.getName())
                .withClaim("email", principal.getEmail())
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC256(secret));
    }

    public UserPrincipal verifyToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String id = decodedJWT.getClaim("id").asString();
        String email = decodedJWT.getClaim("email").asString();

        return new UserPrincipal(Long.parseLong(id), email);
    }
}
