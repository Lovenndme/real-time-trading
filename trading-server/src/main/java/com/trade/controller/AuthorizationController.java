package com.trade.controller;

import com.trade.dto.LoginDTO;
import com.trade.dto.RegisterDTO;
import com.trade.result.Result;
import com.trade.service.AuthorizationService;
import com.trade.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
@Tag(name = "登录注册相关接口")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @GetMapping("/sendCode")
    @Operation(summary = "发送验证码接口")
    public Result<Object> sendCode(@RequestParam @Email String email) {
        authorizationService.sendCode(email);
        return Result.success();
    }

    @PostMapping("/register")
    @Operation(summary = "注册接口")
    public Result<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        authorizationService.register(registerDTO);
        return Result.success();
    }

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = authorizationService.login(loginDTO);
        return Result.success(loginVO);
    }
}
