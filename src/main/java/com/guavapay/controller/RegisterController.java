package com.guavapay.controller;

import com.guavapay.model.dto.FinishedRegistrationDto;
import com.guavapay.model.request.CreateAccountRequest;
import com.guavapay.model.type.RoleType;
import com.guavapay.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("registration")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping(value = "/create")
    protected FinishedRegistrationDto createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return registerService.create(createAccountRequest, RoleType.USER);
    }

    @PostMapping(value = "/create/courier")
    protected FinishedRegistrationDto createCourier(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return registerService.create(createAccountRequest, RoleType.EMPLOYEE);
    }

    @PostMapping(value = "/create/admin")
    protected FinishedRegistrationDto createAdmin(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return registerService.create(createAccountRequest, RoleType.ADMIN);
    }
}
