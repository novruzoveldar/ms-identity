package com.guavapay.security.service;

import com.guavapay.constants.Constants;
import com.guavapay.error.InvalidAccountCredentialsException;
import com.guavapay.model.entity.Account;
import com.guavapay.security.Principal;
import com.guavapay.model.mapper.PrincipalMapper;
import com.guavapay.model.type.AccountState;
import com.guavapay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class AuthProviderService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrincipalMapper principalMapper;

    public Authentication auth(Authentication authentication) {
        Account account = accountRepository.findAccountByPhone((String) authentication.getPrincipal()).orElse(null);

        if (Objects.isNull(account) || account.getState().equals(AccountState.DEACTIVATED) || account.getState().equals(AccountState.PROCESS)) {
            throw new InvalidAccountCredentialsException("Access to account failed.");
        }

        if (account.getState().equals(AccountState.LOCKED)) {
            if (!new Date(account.getAttemptDate().getTime() + 1000 * 1000).before(new Date())) {
                throw new LockedException("Account was locked");
            }
            account.setAttempt(new AtomicInteger(0));
            account.setAttemptDate(null);
            account.setState(AccountState.ACTIVE);
            accountRepository.save(account);
            return auth(authentication);
        }

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), account.getPassword())) {
            account.setAttempt(new AtomicInteger(account.getAttempt().incrementAndGet()));
            account.setAttemptDate(new Date());
            if (account.getAttempt().get() >= Constants.ATTEMPT_COUNT) {
                account.setState(AccountState.LOCKED);
            }
            accountRepository.save(account);
            throw new InvalidAccountCredentialsException("Access to account failed.");
        }

        account.setAttempt(new AtomicInteger(0));
        account.setAttemptDate(null);
        account.setAccessDate(new Date());
        accountRepository.save(account);
        Principal principal = (Principal) principalMapper.toPrincipal(account);
        return new UsernamePasswordAuthenticationToken(principal,
                authentication.getCredentials(),
                principal.getAuthorities());
    }
}
