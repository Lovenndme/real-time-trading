package com.trade.service;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface FeeCalculationService {
    /**
     * 根据转账金额和货币类型计算手续费
     *
     * @param amount    转账金额
     * @param currency  转账货币
     * @param userLevel 用户级别（普通用户、VIP 等）
     * @return 手续费金额
     */
    BigDecimal calculateFee(BigDecimal amount, String currency, String userLevel);

    /**
     * 获取特定货币的手续费费率
     *
     * @param currency 转账货币
     * @return 费率（百分比，例如 0.01 表示 1%）
     */
    BigDecimal getFeeRate(String currency);

    /**
     * 获取手续费的上下限
     *
     * @param currency 转账货币
     * @return 上下限数组（[最小手续费, 最大手续费]）
     */
    BigDecimal[] getFeeLimits(String currency);
}
