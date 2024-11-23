package com.trade.service.impl;

import com.trade.service.FeeCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class FeeCalculationServiceImpl implements FeeCalculationService {
    // 模拟货币手续费率表（货币 -> 手续费率）
    private static final Map<String, BigDecimal> FEE_RATES = new HashMap<>();

    // 模拟手续费上下限表（货币 -> [最小值, 最大值]）
    private static final Map<String, BigDecimal[]> FEE_LIMITS = new HashMap<>();

    static {
        // 手续费率（百分比）
        FEE_RATES.put("USD", BigDecimal.valueOf(0.02)); // 2%
        FEE_RATES.put("EUR", BigDecimal.valueOf(0.015)); // 1.5%
        FEE_RATES.put("CNY", BigDecimal.valueOf(0.01)); // 1%

        // 手续费上下限（[最小手续费, 最大手续费]）
        FEE_LIMITS.put("USD", new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(50)});
        FEE_LIMITS.put("EUR", new BigDecimal[]{BigDecimal.valueOf(2), BigDecimal.valueOf(40)});
        FEE_LIMITS.put("CNY", new BigDecimal[]{BigDecimal.valueOf(0.5), BigDecimal.valueOf(30)});
    }

    /**
     * 根据转账金额、货币和用户级别计算手续费
     */
    @Override
    public BigDecimal calculateFee(BigDecimal amount, String currency, String userLevel) {
        // 获取货币对应的费率
        BigDecimal feeRate = getFeeRate(currency);

        // 基于费率计算手续费
        BigDecimal fee = amount.multiply(feeRate);

        // 获取手续费上下限
        BigDecimal[] limits = getFeeLimits(currency);
        BigDecimal minFee = limits[0];
        BigDecimal maxFee = limits[1];

        // 适用上下限规则
        if (fee.compareTo(minFee) < 0) {
            fee = minFee;
        } else if (fee.compareTo(maxFee) > 0) {
            fee = maxFee;
        }

        // 对 VIP 用户的手续费进行优惠（示例：普通用户不优惠，VIP 用户优惠 20%）
        if ("VIP".equalsIgnoreCase(userLevel)) {
            fee = fee.multiply(BigDecimal.valueOf(0.8)); // 优惠 20%
        }

        return fee.setScale(2, RoundingMode.HALF_UP); // 保留两位小数
    }

    /**
     * 获取货币的手续费费率
     */
    @Override
    public BigDecimal getFeeRate(String currency) {
        return FEE_RATES.getOrDefault(currency, BigDecimal.valueOf(0.02)); // 默认费率 2%
    }

    /**
     * 获取手续费的上下限
     */
    @Override
    public BigDecimal[] getFeeLimits(String currency) {
        return FEE_LIMITS.getOrDefault(currency, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.valueOf(100)});
    }
}
