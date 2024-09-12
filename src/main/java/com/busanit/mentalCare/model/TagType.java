package com.busanit.mentalCare.model;

// 게시판 타입을 결정하기 위한 enum
public enum TagType {
    COMMON("COMMON"), MENTAL("MENTAL"), CHEERING("CHEERING");
    private String value;

    TagType(String value) {
        this.value = value;
    }
}
