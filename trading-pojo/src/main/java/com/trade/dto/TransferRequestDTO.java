package com.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*
  TransferRequestDTO类用于表示转账请求的数据传输对象
  它实现了Serializable接口，以便它可以被序列化和反序列化，通常用于网络通信或存储
 */
public class TransferRequestDTO implements Serializable {

    // 发起转账的用户ID
    private String senderId;
    // 接收转账的用户ID
    private String receiverId;
    // 转账金额的源货币类型
    private String sourceCurrency;
    // 转账金额的目标货币类型
    private String targetCurrency;
    // 转账的金额数目，使用BigDecimal以精确表示金融数据
    private BigDecimal amount;
    // 转账的描述信息，用于提供额外的转账上下文或备注
    private String description;
}

