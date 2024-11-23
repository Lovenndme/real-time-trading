package com.trade.controller;

import com.trade.dto.TransferRequestDTO;
import com.trade.result.Result;
import com.trade.service.TransferService;
import com.trade.vo.TransferResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transfer")
@Tag(name = "转账相关接口")
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    @Operation(summary = "转账接口")
    public Result<TransferResponseVO> transfer(@RequestBody TransferRequestDTO request) {
        TransferResponseVO response = transferService.executeTransfer(request);
        return Result.success(response);
    }
}
