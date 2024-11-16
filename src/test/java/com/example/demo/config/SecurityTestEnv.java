package com.example.demo.config;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@ActiveProfiles("test")
@Disabled
public class SecurityTestEnv {
}
