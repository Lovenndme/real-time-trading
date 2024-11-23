package com.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("transfer")
public class Transfer {

    /**
     * 交易记录类
     * 该类记录了每一次交易的详细信息，包括交易双方、货币类型、交易金额等
     */
    private Long id;
    /**
     * 交易标识符
     * 用于唯一标识一笔交易
     */
    private String transactionId;
    /**
     * 发起交易的用户
     * 表示资金的转出方
     */
    private User sender;
    /**
     * 接收交易的用户
     * 表示资金的转入方
     */
    private User receiver;
    /**
     * 源货币类型
     * 表示交易发起时的货币类型
     */
    private String sourceCurrency;
    /**
     * 目标货币类型
     * 表示交易完成后转换成的货币类型
     */
    private String targetCurrency;
    /**
     * 交易金额
     * 表示交易发起时的金额数目
     */
    private BigDecimal amount;
    /**
     * 转换后的金额
     * 根据汇率转换后的目标货币金额
     */
    private BigDecimal convertedAmount;
    /**
     * 汇率
     * 源货币兑换目标货币的汇率
     */
    private BigDecimal exchangeRate;
    /**
     * 手续费
     * 完成交易所需支付的费用
     */
    private BigDecimal fee;
    /**
     * 交易状态
     * 描述交易当前的状态，如成功、失败、处理中等
     */
    private String status;
    /**
     * 交易描述
     * 对交易的简要描述，可以包括交易目的、备注等信息
     */
    private String description;
    /**
     * 创建交易的时间
     * 记录交易创建的时间点
     */
    private LocalDateTime createdAt;
}
