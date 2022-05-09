package com.guavapay.model.entity;

import com.guavapay.model.converter.AtomicDecimalConverter;
import com.guavapay.model.type.WalletState;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "seq_wallet", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "create_date", nullable = false)
    private Date createDate = new Date();

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "available_balance", nullable = false)
    @Convert(converter = AtomicDecimalConverter.class)
    private AtomicReference<BigDecimal> balance;

    @Column(nullable = false)
    private Integer currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletState state;

    @Column(name = "account_id", nullable = false, unique = true)
    private Long accountId;
}
