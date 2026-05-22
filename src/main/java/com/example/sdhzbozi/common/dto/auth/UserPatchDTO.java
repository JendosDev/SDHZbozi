package com.example.sdhzbozi.common.dto.auth;

import com.example.sdhzbozi.common.enums.RoleEnum;

public record UserPatchDTO (
        Integer id,
        String firstname,
        String surname,
        String email,
        RoleEnum role
){
}
