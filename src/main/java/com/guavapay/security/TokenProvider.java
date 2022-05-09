package com.guavapay.security;

import com.guavapay.model.dto.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${application.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    @Value("${application.security.authentication.jwt.secret}")
    private String secret;

    private static final String FULL_NAME = "title";
    private static final String AUTHORITY_IDENTIFIER = "role";
    private static final String STATUS = "status";
    private static final String IDENTIFIER = "id";

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenPair createToken(Principal principal) {
        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .claim(FULL_NAME, principal.getFullName())
                .claim(AUTHORITY_IDENTIFIER, principal.getAuthorities().stream()
                        .map(Object::toString).collect(Collectors.joining(":")))
                .claim(STATUS, principal.getStatus())
                .claim(IDENTIFIER, principal.getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date((new Date()).getTime() + tokenValidityInSeconds * 1000))
                .compact();
        return TokenPair.builder()
                .accessToken(token)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITY_IDENTIFIER).toString().split(":"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(Principal.builder()
                .id(Long.valueOf(String.valueOf(claims.get(IDENTIFIER))))
                .login(claims.getSubject())
                .fullName(claims.get(FULL_NAME).toString())
                .status((Integer) claims.get(STATUS))
                .authorities(authorities)
                .build(), token, authorities);
    }

    public void validateToken(String authToken) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
    }
}
