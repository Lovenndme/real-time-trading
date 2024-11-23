package com.trade.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.constant.JwtClaimsConstant;
import com.trade.constant.MessageConstant;
import com.trade.dto.LoginDTO;
import com.trade.dto.RegisterDTO;
import com.trade.entity.LoginUser;
import com.trade.entity.User;
import com.trade.exception.LoginErrorException;
import com.trade.exception.VerificationErrorException;
import com.trade.mapper.AuthorizationMapper;
import com.trade.mapper.UserMapper;
import com.trade.properties.JwtProperties;
import com.trade.service.AuthorizationService;
import com.trade.utils.EmailUtil;
import com.trade.utils.JwtUtil;
import com.trade.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl extends ServiceImpl<UserMapper, User> implements AuthorizationService {

    private final EmailUtil emailUtil;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtProperties jwtProperties;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final RabbitTemplate rabbitTemplate;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthorizationMapper authorizationMapper;

    /**
     * 发送验证码接口
     *
     * @param email 接收验证码邮箱
     */
    public void sendCode(String email) {
        log.info("接收验证码邮箱：{}", email);
        String i = emailUtil.codeMail(email) + "";
        redisTemplate.opsForValue().set("verification:code:" + email, i, 5, TimeUnit.MINUTES);
    }

    /**
     * 注册接口
     *
     * @param registerDTO 注册信息
     */
    @Transactional
    public void register(RegisterDTO registerDTO) {
        //执行验证码判断
        String code = Objects.requireNonNull(redisTemplate.opsForValue().get("verification:code" + registerDTO.getEmail())).toString();
        if (!Objects.equals(registerDTO.getCode(), code)) {
            throw new VerificationErrorException(MessageConstant.VERIFICATION_ERROR);
        }
        log.info("注册用户信息：{}", registerDTO);
        User user = User.builder()
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .nation(registerDTO.getNation())
                .telephone(registerDTO.getTelephone())
                .createTime(LocalDateTime.now())
                .username(registerDTO.getUsername())
                .updateTime(LocalDateTime.now()).build();
        authorizationMapper.register(user);
        Long id = user.getId();
        authorizationMapper.init(id);
    }

    /**
     * 登录接口
     *
     * @param loginDTO 登录信息
     * @return 用户身份信息
     */
    public LoginVO login(LoginDTO loginDTO) {
        //AuthenticationManager进行用户认证
        Authentication authenticate = getAuthentication(loginDTO);
        //在Authentication中获取用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User u = loginUser.getUser();
        LoginVO loginVO = null;
        if (u != null) {
            loginVO = LoginVO.builder()
                    .id(u.getId())
                    .build();
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.ID, loginVO.getId());
            String jwt = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
            loginVO.setToken(jwt);
            String userId = u.getId().toString();
            //用户信息存入redis
            redisTemplate.opsForValue().set("login:" + userId, loginUser);
            //进行风控校验
            executor.submit(() -> {
                String s = JSONObject.toJSONString(u);
                String exchangeName = "loginSafety.direct";
                String routingKey = "loginSafetyDirect";
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend(exchangeName, routingKey, s, message -> {
                    message.getMessageProperties().setMessageId(correlationData.getId());
                    return message;
                }, correlationData);
            });
        }
        return loginVO;
    }

    /**
     * 认证
     *
     * @param loginDTO 用户登录信息
     * @return 用户认证结果
     */
    private Authentication getAuthentication(LoginDTO loginDTO) {
        AuthenticationManager authenticationManager;
        try {
            authenticationManager = authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmailOrTelephone(), loginDTO.getPassword());
        Authentication authenticate;
        //如果认证没通过，给出对应提示
        try {
            authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            throw new LoginErrorException(MessageConstant.INFORMATION_ERROR);
        }
        return authenticate;
    }
}
