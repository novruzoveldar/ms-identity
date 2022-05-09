package com.guavapay.util;

import java.util.UUID;

public class WalletUtil {

    private static final String KEY = "PD";

    public static String generateAccountNumber() {
        return KEY + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
