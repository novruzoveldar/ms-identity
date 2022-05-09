package com.guavapay.service;

import com.guavapay.model.entity.Account;
import com.guavapay.model.entity.Wallet;
import com.guavapay.model.type.CurrencyType;
import com.guavapay.model.type.WalletState;
import com.guavapay.repository.WalletRepository;
import com.guavapay.util.WalletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void createWallet(Account account) {
        Wallet wallet = walletRepository.findByAccountId(account.getId()).orElse(null);
        if (Objects.isNull(wallet)) {
            walletRepository.save(Wallet.builder()
                    .accountId(account.getId())
                    .accountNumber(WalletUtil.generateAccountNumber())
                    .balance(new AtomicReference<>(BigDecimal.ZERO))
                    .createDate(new Date())
                    .currency(CurrencyType.AZN.code())
                    .state(WalletState.OPEN)
                    .build());
        }
    }
}
