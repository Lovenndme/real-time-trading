package com.trade.schedule;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.entity.FundPool;
import com.trade.mapper.AdminMapper;
import com.trade.websocket.EndPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledTasks extends ServiceImpl<AdminMapper, FundPool>{

    private final AdminMapper adminMapper;

    private final EndPoint endPoint;

    @Scheduled(fixedRate = 60000)
    public void runTask1() {
        List<FundPool> fundPools = adminMapper.selectList(null);
        for (FundPool fundPool : fundPools) {
            if(fundPool.getRemaining() <= 450000){
               List<Long> ids = adminMapper.getAllAdminId();
                endPoint.sendWarningToAdmin(fundPool.getFundType(),ids);
            }
        }
    }


}