package com.guavapay.service;

import com.guavapay.model.dto.FinishedRegistrationDto;
import com.guavapay.model.entity.Account;
import com.guavapay.model.mapper.AccountMapper;
import com.guavapay.model.request.CreateAccountRequest;
import com.guavapay.model.type.AccountState;
import com.guavapay.model.type.RoleType;
import com.guavapay.repository.AccountRepository;
import com.guavapay.repository.RoleRepository;
import com.guavapay.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RoleRepository roleRepository;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;
    private final CourierService courierService;

    @Transactional
    public FinishedRegistrationDto create(CreateAccountRequest createAccountRequest, RoleType roleType) {
        log.info("Searching account in DB. phone {}", createAccountRequest.getLogin());
        Account account = accountRepository.findAccountByPhone(createAccountRequest.getLogin()).orElse(null);
        if (Objects.isNull(account)) {
            account = createAccount(createAccountRequest, roleType.name());
        } else {
            throw new IllegalStateException("User already exists");
        }

        log.info("Creating wallet for account. accountId {}", account.getId());
        walletService.createWallet(account);

        if(Objects.equals(roleType, RoleType.EMPLOYEE)) courierService.createCourier(account, createAccountRequest.getCourierType());

        return FinishedRegistrationDto.builder()
                .token(account.getRegistrationToken())
                .build();
    }

    private Account createAccount(CreateAccountRequest createAccountRequest, String role) {
        log.info("Creating new user. userPhone {}", createAccountRequest.getLogin());

        Account account = accountMapper.toAccountEntity(createAccountRequest);
        account.setState(AccountState.ACTIVE);
        account.setCreateDate(new Date());

        log.info("Generating account password. login {}", createAccountRequest.getLogin());
        account.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
        account.setRegistrationToken(AccountUtil.generateToken());
        account.setRoles(Collections.singleton(roleRepository.getById(RoleType.valueOf(role).value())));
        return accountRepository.save(account);
    }
}
