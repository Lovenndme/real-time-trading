package com.trade.controller;

import com.trade.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理相关接口")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/getFundPool")
    @Operation(summary = "获取最近基金池流动情况")
    public Result<List<Long>> getFundPoolInformation(@RequestParam Integer type){
        return Result.success(new ArrayList<>());
    }
}
