package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.JwtUtil;
import com.example.emos.wx.controller.form.RegisterForm;
import com.example.emos.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController()
@Api("用户模块web接口")
@RequestMapping("/user")
public class UserController {

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R registerUser(@Valid @RequestBody RegisterForm form){
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPhoto());
        String token = jwtUtil.createToken(id);
        saveCacheToken(token,id);
        Set<String> permisSet = userService.searchUserPermissions(id);
        return R.ok("注册用户成功").put("token",token).put("permission",permisSet);
    }

    private void saveCacheToken(String token,int id){
        redisTemplate.opsForValue().set(token,id+"",cacheExpire, TimeUnit.DAYS);
    }


}
