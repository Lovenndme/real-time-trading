package com.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.entity.UserQrRemaining;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface TradeMapper extends BaseMapper<UserQrRemaining> {

    /**
     * 设置用户的支付码
     * @param url 支付码的网络路径
     */
    @Update("update user_qr_remaining set qr = #{url}")
    void saveQr(String url);
}
