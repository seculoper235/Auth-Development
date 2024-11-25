package com.example.demo.config;

import com.example.demo.domain.JwtProvider;
import org.junit.jupiter.api.Disabled;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("test")
@SpringJUnitConfig({JwtProvider.class})
@Disabled
public class ComponentTestEnv {
}
