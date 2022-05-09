package com.guavapay.util;

import java.util.UUID;

public class AccountUtil {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
