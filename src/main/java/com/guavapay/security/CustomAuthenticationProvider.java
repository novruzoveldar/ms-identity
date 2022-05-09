package com.guavapay.security;

import com.guavapay.security.service.AuthProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthProviderService authProviderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authProviderService.auth(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
