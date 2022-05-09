package com.guavapay.model.type;

public enum CurrencyType {

    AZN(944),
    USD(840),
    EUR(978);

    private int code;

    CurrencyType(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
