package com.example.demo.web.controller.app;

import com.example.demo.model.app.AppUser;
import com.example.demo.service.app.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable("id") Long id) {
        AppUser appUser = appUserService.find(id);

        return ResponseEntity.ok(appUser);
    }
}
