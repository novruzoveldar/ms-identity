package com.guavapay.service;

import com.guavapay.model.entity.Account;
import com.guavapay.model.entity.Courier;
import com.guavapay.model.type.AvailabilityState;
import com.guavapay.model.type.CourierType;
import com.guavapay.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    public void createCourier(Account account, CourierType courierType) {
        Courier courier = courierRepository.findByAccountId(account.getId()).orElse(null);
        if (Objects.isNull(courier)) {
            courierRepository.save(Courier.builder()
                    .account(account)
                    .availabilityState(AvailabilityState.READY)
                    .type(courierType)
                    .build());
        }
    }
}
