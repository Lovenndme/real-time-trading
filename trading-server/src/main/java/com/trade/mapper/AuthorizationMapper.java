package com.trade.mapper;

import com.trade.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface AuthorizationMapper {

    /**
     * 获取用户的权限信息
     *
     * @param id 用户id
     * @return 用户权限信息
     */
    List<String> getAuthority(Long id);

    /**
     * 注册
     *
     * @param user 注册的用户信息
     */
    @Insert("INSERT INTO users (username, password, telephone, email, nation, image, create_time, update_time) VALUES (#{username}, #{password}, #{telephone}, #{email}, #{nation}, #{image}, #{create_time}, #{update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void register(User user);

    /**
     * 存储用户信息
     *
     * @param id 用户id
     */
    @Insert("INSERT INTO user_qr_remaining (id) VALUES (#{id})")
    void init(Long id);
}
