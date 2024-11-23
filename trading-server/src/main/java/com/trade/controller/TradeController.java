package com.trade.controller;

import com.trade.result.Result;
import com.trade.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
@Tag(name = "支付相关接口")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/paymentCode")
    @Operation(summary = "生成支付码接口")
    public Result<String> generatePaymentCode(){
        String QRCode =  tradeService.generatePaymentCode();
        return Result.success(QRCode);
    }



}
