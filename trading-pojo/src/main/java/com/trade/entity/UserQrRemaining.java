package com.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityScan
@TableName("user_qr_remaining")
public class UserQrRemaining {

    private Long id;

    private String qr;

    private Long remaining;
}
