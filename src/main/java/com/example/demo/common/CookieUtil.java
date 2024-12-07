package com.example.demo.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(name))
                .findFirst().map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException(name+ " not found"));
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
