package com.htzh.htdxjk.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Rechel
 * @Date: 2019/3/27 下午6:57
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"会话保持"})
@RequestMapping("/api/keepSession")
public class KeepSessionController {

    @GetMapping("keepSessionOn")
    public String keepSessionOn(){
        return "ok";
    }
}
