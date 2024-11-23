package com.trade.controller;

import com.trade.result.Result;
import com.trade.service.UserService;
import com.trade.vo.UserQueryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "查询用户信息")
    public Result<UserQueryVO> query() {
        UserQueryVO vo = userService.queryUser();
        return Result.success(vo);
    }

}
