package com.example.sdhzbozi.common.enums;

public enum RoleEnum {
    UNDEFINED,
    ADMIN,
    PARENT,
    CHILD,
    MEMBER;

    public String authority() {
        return "ROLE_" + name();
    }
}
