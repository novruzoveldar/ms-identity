package com.guavapay.error;

public class InvalidAccountCredentialsException extends BaseException {

    public InvalidAccountCredentialsException(String errorMessage) {
        super(ErrorCode.INVALID_ACCOUNT_CREDENTIAL, errorMessage);
    }
}
