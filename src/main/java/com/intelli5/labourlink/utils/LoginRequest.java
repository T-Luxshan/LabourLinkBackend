package com.intelli5.labourlink.utils;

import com.intelli5.labourlink.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private UserRole role;
    private String email;
    private String password;
}
