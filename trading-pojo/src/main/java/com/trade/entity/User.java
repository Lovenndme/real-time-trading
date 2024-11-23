package com.trade.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String telephone;

    private String email;

    private Byte sex;

    private String nation;

    private String image;

    private String payPassword;

    private String realName;

    private String idCard;

    private Integer score;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}