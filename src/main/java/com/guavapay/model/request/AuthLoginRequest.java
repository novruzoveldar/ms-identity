package com.guavapay.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthLoginRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
