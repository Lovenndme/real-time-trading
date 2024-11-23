package com.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.dto.TransferRequestDTO;
import com.trade.entity.Transfer;
import com.trade.vo.TransferResponseVO;

public interface TransferService extends IService<Transfer> {
    TransferResponseVO executeTransfer(TransferRequestDTO request);
}
