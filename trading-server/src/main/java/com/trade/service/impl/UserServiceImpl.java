package com.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.entity.LoginUser;
import com.trade.entity.User;
import com.trade.mapper.UserMapper;
import com.trade.service.UserService;
import com.trade.vo.UserQueryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public UserQueryVO queryUser() {
        // 获取当前登录用户的ID
        Long userId = getCurrentUserId();

        // 根据用户ID查询用户信息
        User user = getById(userId);

        // 对用户信息进行处理
        String maskedEmail = maskEmail(user.getEmail());
        String maskedTelephone = maskTelephone(user.getTelephone());
        String maskedPassword = maskPassword(user.getPassword());

        // 封装到 UserQueryVO 并返回
        return UserQueryVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(maskedEmail)
                .telephone(maskedTelephone)
                .password(maskedPassword)
                .build();
    }

    /**
     * 获取当前登录用户的ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getId();
    }

    /**
     * 对邮箱进行掩码处理
     * 规则：展示邮箱前两个字符和@后面的域名，中间部分用*代替
     * 例如：ab***@example.com
     */
    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email; // 返回原始值以防意外
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            // 如果@前长度小于等于2，则只展示第一个字符
            return email.charAt(0) + "****" + email.substring(atIndex);
        }
        return email.substring(0, 2) + "****" + email.substring(atIndex);
    }

    /**
     * 对电话号码进行掩码处理
     * 规则：显示前3位和后4位，中间用*代替
     * 如果电话号码为空，返回提示信息 “To be added”
     * 例如：123****5678 或 To be added
     */
    private String maskTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return "To be added"; // 返回默认提示信息
        }
        if (telephone.length() < 7) {
            return telephone; // 返回原始值以防意外
        }
        return telephone.substring(0, 3) + "****" + telephone.substring(telephone.length() - 4);
    }

    /**
     * 对密码进行全掩码处理
     * 规则：用固定长度的*代替
     * 例如：********
     */
    private String maskPassword(String password) {
        // 默认返回全掩码
        return "********"; // 始终返回固定长度的掩码
    }
}
