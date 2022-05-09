package com.guavapay.constants;

public interface Constants {

    String PWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*?[0-9]).{8,100}$";
    String INVALID_TOKEN = "BT100";
    String TOKEN_CACHE_PREFIX = "accessToken";
    int ATTEMPT_COUNT = 3;
}
