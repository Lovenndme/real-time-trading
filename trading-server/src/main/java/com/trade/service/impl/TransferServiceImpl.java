package com.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.dto.TransferRequestDTO;
import com.trade.entity.Transfer;
import com.trade.entity.User;
import com.trade.entity.UserQrRemaining;
import com.trade.mapper.TradeMapper;
import com.trade.mapper.TransferMapper;
import com.trade.mapper.UserMapper;
import com.trade.service.CurrencyExchangeService;
import com.trade.service.FeeCalculationService;
import com.trade.service.TransferService;
import com.trade.vo.TransferResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements TransferService {

    private final UserMapper userMapper;
    private final TradeMapper tradeMapper;
    private final CurrencyExchangeService exchangeService;
    private final FeeCalculationService feeService;

    @Override
    @Transactional
    public TransferResponseVO executeTransfer(TransferRequestDTO request) {
        // Step 1: 校验账户余额和用户信息
        validateUser(request.getSenderId(), request.getAmount());
        // TODO 获取实时汇率并计算目标金额
        // TODO 计算手续费
        BigDecimal fee = feeService.calculateFee(request.getAmount(), request.getSourceCurrency(), "DEFAULT");
        // Step 4: 执行转账（扣款并记账）
        // Step 5: 返回结果
        return new TransferResponseVO();
    }

    private void validateUser(String senderId, BigDecimal amount) {
        // 校验用户账户余额和身份
        User sender = userMapper.selectById(senderId);
        if (sender == null) {
            throw new IllegalArgumentException("Sender user does not exist");
        }
        Long userId = sender.getId();
        UserQrRemaining userQrRemaining = tradeMapper.selectById(userId);
        Long balance = userQrRemaining.getRemaining();
        // TODO 剩余校验逻辑

    }

    private void processTransaction(TransferRequestDTO request, BigDecimal convertedAmount, BigDecimal fee) {
        // TODO 扣减发起方账户余额并增加接收方账户余额
    }
}
