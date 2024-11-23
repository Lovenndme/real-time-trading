package com.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.dto.UserModifyDTO;
import com.trade.entity.User;
import com.trade.vo.UserQueryVO;

public interface UserService extends IService<User> {

    UserQueryVO findMe();

    void userUpdate(UserModifyDTO userModifyDTO);
}
