package com.shurygin.englishroad.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    private final String username;
    private final String password;
}
