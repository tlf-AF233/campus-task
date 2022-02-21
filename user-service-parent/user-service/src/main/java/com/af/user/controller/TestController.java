package com.af.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tanglinfeng
 * @date 2022/2/7 15:58
 */
@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping("test")
    public String test() {
        return "hello, user";
    }
}
