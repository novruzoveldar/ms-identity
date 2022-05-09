package com.guavapay.error;

import com.guavapay.config.i18n.Translator;
import com.guavapay.model.dto.RestBaseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class ErrorHandler {

    private final Translator translator;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAccountCredentialsException.class)
    public RestBaseError handleInvalidAccountCredentialsException(InvalidAccountCredentialsException ex) {
        log.error("Invalid account credentials. errorCode {} , errorMessage {}", ex.getErrorCode(), ex.getErrorMessage());

        return new RestBaseError(
                ex.getErrorCode(),
                translator.toLocale(ex.getErrorCode()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountLockedException.class)
    public RestBaseError handleAccountLockedException(AccountLockedException ex) {
        log.error("Account was locked. errorCode {} , errorMessage {}", ex.getErrorCode(), ex.getErrorMessage());

        return new RestBaseError(
                ex.getErrorCode(),
                translator.toLocale(ex.getErrorCode()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountNotFoundException.class)
    public RestBaseError handleAccountNotFoundException(AccountNotFoundException ex) {
        log.error("Account not found. errorCode {} , errorMessage {}", ex.getErrorCode(), ex.getErrorMessage());

        return new RestBaseError(
                ex.getErrorCode(),
                translator.toLocale(ex.getErrorCode()));
    }
}
