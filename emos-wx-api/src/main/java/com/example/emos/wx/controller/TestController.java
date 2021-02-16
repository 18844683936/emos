package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.controller.form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/*
* 这里要用RestController，或者返回值为json类型，
* 要不报404*/
@RestController
@RequestMapping("/test")
@Api("测试Swagger类")
public class TestController {
    @ApiOperation("sayHello")
    @PostMapping("/sayHello")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form){
        return R.ok().put("date",new Date()).put("name",form.getName());
    }
}
