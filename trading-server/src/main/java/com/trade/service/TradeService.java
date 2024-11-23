package com.trade.service;

import org.springframework.stereotype.Service;

@Service
public interface TradeService {

    String generatePaymentCode();
}
