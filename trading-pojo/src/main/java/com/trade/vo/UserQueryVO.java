package com.trade.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQueryVO {
    private Long id;
    private String username;
    private String email;
    private String telephone;
    private String password;

}
