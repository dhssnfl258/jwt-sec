package com.example.jwtsecurityfin.domain.account;

// 지출 유형
public enum CATEGORY {
    숙소("Accommodation"),
    항공("Flight"),
    교통("Transportation"),
    식비("Food"),
    관광("Sightseeing"),
    쇼핑("Shopping"),
    기타("Others");

    private final String value;

    CATEGORY(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
