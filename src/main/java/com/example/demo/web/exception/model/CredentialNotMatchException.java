package com.example.demo.web.exception.model;

public class CredentialNotMatchException extends Exception {

    public CredentialNotMatchException() {
        super("아이디나 비밀번호가 일치하지 않습니다.");
    }
}
