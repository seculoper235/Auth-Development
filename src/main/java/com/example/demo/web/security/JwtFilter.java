package com.example.demo.web.security;

import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.token.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    private final JwtProvider jwtProvider;

    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
                     JwtProvider jwtProvider) {
        this.resolver = resolver;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> accessToken = Optional.ofNullable(request.getHeader("Authorization"))
                    .filter(token -> token.startsWith("Bearer "))
                    .map(token -> token.substring(7));
            if (accessToken.isPresent()) {
                UserPrincipal principal = jwtProvider.verifyToken(accessToken.get());

                // TODO: 사용자 권한 임시 지정
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principal, "", authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}
