package com.trade.controller;

import com.trade.dto.UserModifyDTO;
import com.trade.result.Result;
import com.trade.service.UserService;
import com.trade.vo.UserQueryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "查询用户信息")
    public Result<UserQueryVO> me() {
        UserQueryVO vo = userService.findMe();
        return Result.success(vo);
    }

    @PutMapping
    @Operation(summary = "修改用户信息")
    public Result<Object> updateUser(@RequestBody @Valid UserModifyDTO userModifyDTO) {
        userService.userUpdate(userModifyDTO);
        return Result.success();
    }

}
