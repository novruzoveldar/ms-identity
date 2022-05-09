package com.guavapay.service;

import com.guavapay.cache.TokenStorage;
import com.guavapay.model.dto.TokenPair;
import com.guavapay.model.request.AuthLoginRequest;
import com.guavapay.security.Principal;
import com.guavapay.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AuthenticationManager manager;
    private final TokenProvider tokenProvider;
    private final TokenStorage tokenStorage;

    public TokenPair login(AuthLoginRequest authLoginRequest) {
        UsernamePasswordAuthenticationToken passwordAuthentication = new UsernamePasswordAuthenticationToken(
                authLoginRequest.getLogin(), authLoginRequest.getPassword());

        Authentication authentication = manager.authenticate(passwordAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Principal principal = (Principal) authentication.getPrincipal();

        TokenPair tokenPair = tokenProvider.createToken(principal);
        tokenStorage.save(tokenPair, principal.getId());
        return tokenPair;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) authentication.getPrincipal();

        log.info("Account logged out and deleting token. accountId {}", principal.getId());
        tokenStorage.delete(principal.getId());
    }
}
