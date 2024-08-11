package com.busanit.mentalCare.entity;

public enum TagType {
    COMMON("COMMON"), MENTAL("MENTAL"), CHEERING("CHEERING");
    private String value;

    TagType(String value) {
        this.value = value;
    }
}
