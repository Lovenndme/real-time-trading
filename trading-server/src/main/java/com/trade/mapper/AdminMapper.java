package com.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.entity.FundPool;
import com.trade.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<FundPool> {

    /**
     * 获取所有管理id
     * @return 管理id
     */
    @Select("select user_id from user_role where role_id = 5")
    List<Long> getAllAdminId();
}
