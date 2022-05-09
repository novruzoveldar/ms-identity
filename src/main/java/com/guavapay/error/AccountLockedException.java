package com.guavapay.error;

public class AccountLockedException extends BaseException {

    public AccountLockedException(String errorMessage) {
        super(ErrorCode.ACCOUNT_LOCKED, errorMessage);
    }
}
