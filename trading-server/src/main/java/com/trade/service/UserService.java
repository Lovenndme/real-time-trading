package com.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.entity.User;
import com.trade.vo.UserQueryVO;

public interface UserService extends IService<User> {
    UserQueryVO queryUser();
}
