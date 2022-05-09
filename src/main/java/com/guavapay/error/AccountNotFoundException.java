package com.guavapay.error;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(String errorMessage) {
        super(ErrorCode.ACCOUNT_NOT_FOUND, errorMessage);
    }
}
