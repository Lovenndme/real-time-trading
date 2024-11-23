package com.trade.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TransferResponseVO类用于表示转账响应的信息
 * 它封装了与转账交易相关的数据，包括交易ID、状态、转换金额、汇率、费用和时间戳
 */
public class TransferResponseVO {
    /**
     * 交易ID，唯一标识一次转账交易
     */
    private String transactionId;

    /**
     * 转账交易的状态，例如成功、失败或处理中
     */
    private String status;

    /**
     * 转换后的金额，即实际转账的金额
     */
    private BigDecimal convertedAmount;

    /**
     * 汇率，用于计算源货币到目标货币的转换
     */
    private BigDecimal exchangeRate;

    /**
     * 转账交易的费用，可能包括手续费或其他相关费用
     */
    private BigDecimal fee;

    /**
     * 时间戳，记录转账交易响应的时刻
     */
    private LocalDateTime timestamp;
}

