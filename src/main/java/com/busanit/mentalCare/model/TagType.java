package com.busanit.mentalCare.model;

// 게시판 타입을 결정하기 위한 enum
public enum TagType {
    COMMON("COMMON"), MENTAL("MENTAL"), CHEERING("CHEERING");
    private String value;

    TagType(String value) {
        this.value = value;
    }

    /* 게시판 타입은 총 3가지 유형으로 나눔
    * 1. COMMON : 일반고민 게시판
    * 2. MENTAL : 정신건강 게시판
    * 3. CHEERING : 응원 게시판
    * ※ 기본 설정은 COMMON 게시판으로 설정 */
}
