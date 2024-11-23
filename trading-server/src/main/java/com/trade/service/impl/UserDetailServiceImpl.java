package com.trade.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.constant.MessageConstant;
import com.trade.entity.LoginUser;
import com.trade.entity.User;
import com.trade.exception.LoginErrorException;
import com.trade.mapper.AuthorizationMapper;
import com.trade.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl extends ServiceImpl<UserMapper,User> implements UserDetailsService {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final AuthorizationMapper authorizationMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!Objects.isNull(redisTemplate.opsForValue().get("user doesn't exist:" + username))) {
            throw new LoginErrorException(MessageConstant.INFORMATION_ERROR);
        }
        User user;
        if (!username.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            user = query().eq("telephone", username).one();
        } else {
            user = query().eq("email", username).one();
        }
        if (Objects.isNull(user)) {
            //防止缓存穿透，缓存空数据并设置过期时间
            redisTemplate.opsForValue().set("user doesn't exist:" + username, "1", 25 + RandomUtil.randomInt(10), TimeUnit.MINUTES);
            throw new LoginErrorException(MessageConstant.INFORMATION_ERROR);
        }
        List<String> list = authorizationMapper.getAuthority(user.getId());
        //把数据封装为UserDetails对象返回
        return new LoginUser(user, list);
    }

}