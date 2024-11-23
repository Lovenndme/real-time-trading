package com.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityScan
@TableName("fund_pool")
public class FundPool implements Serializable {

    private Long id;

    private String fundType;

    private Long remaining;

    private LocalDateTime addTime;
}