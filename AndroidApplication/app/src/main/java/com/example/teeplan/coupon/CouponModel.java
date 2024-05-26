package com.example.teeplan.coupon;

public class CouponModel {
    private String name;
    private String description;

    private String code;

    public CouponModel(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
