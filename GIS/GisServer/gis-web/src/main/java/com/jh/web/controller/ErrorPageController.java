package com.jh.web.controller;

import com.jh.utils.ajax.AjaxResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPageController implements ErrorController {
    @GetMapping("/error")
    @PostMapping("/error")
    public String error() {
        return AjaxResult.error("出错啦！");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
