package com.example.demo.web.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OauthSuccessHandler oauthSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers((header) ->
                        // 동일 출처 요청 설정
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND)))
                // 인가 설정(각 패턴에 대해 접근 권한 설정)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                AntPathRequestMatcher
                                        .antMatcher(HttpMethod.POST, "/api/auth/**")
                        ).permitAll()
                        .requestMatchers(
                                AntPathRequestMatcher
                                        .antMatcher(HttpMethod.GET, "/api/auth/user/**")
                        ).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Client(Customizer.withDefaults())
//                .oauth2Login(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth -> oauth
                        .successHandler(oauthSuccessHandler)
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // 특정 패턴에 대해 CORS 정책 적용
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", getCorsConfiguration());

        return corsSource;
    }

    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 특정 Origins 허용
        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173"
                ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"));
        configuration.setExposedHeaders(List.of("Authorization", "refresh"));

        return configuration;
    }
}
