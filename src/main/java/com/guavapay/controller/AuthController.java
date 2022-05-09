package com.guavapay.controller;

import com.guavapay.model.dto.TokenPair;
import com.guavapay.model.request.AuthLoginRequest;
import com.guavapay.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final ProfileService profileService;

    @PostMapping(value = "/login")
    protected TokenPair login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
        return profileService.login(authLoginRequest);
    }

    @PostMapping(value = "/logout")
    protected ResponseEntity<String> login() {
        profileService.logout();
        return ResponseEntity.ok(null);
    }
}
