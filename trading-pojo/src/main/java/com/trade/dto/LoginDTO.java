package com.trade.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {

    @NotNull
    private String emailOrTelephone;

    @NotNull
    private String password;

    @NotNull
    private String IP;
}
