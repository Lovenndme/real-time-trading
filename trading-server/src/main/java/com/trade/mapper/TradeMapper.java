package com.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.entity.UserQrRemaining;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface TradeMapper extends BaseMapper<UserQrRemaining> {

    @Update("update user_qr_remaining set qr = #{url}")
    void saveQr(String url);
}
