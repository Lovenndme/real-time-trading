package com.trade.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO  implements Serializable {

    @NotNull
    private String username;

    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String telephone;

    @NotNull
    private String code;

    @NotNull
    private String nation;

}
