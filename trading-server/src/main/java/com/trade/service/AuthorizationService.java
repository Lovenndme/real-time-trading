package com.trade.service;

import com.trade.dto.LoginDTO;
import com.trade.dto.RegisterDTO;
import com.trade.vo.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {

    void sendCode(String email);

    void register(RegisterDTO registerDTO);

    LoginVO login(LoginDTO loginDTO);
}
