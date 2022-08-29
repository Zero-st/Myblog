package com.st.myblog.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.st.myblog.common.dto.LoginDto;
import com.st.myblog.entity.Result;
import com.st.myblog.entity.User;
import com.st.myblog.service.UserService;
import com.st.myblog.shiro.utils.JwtUtils;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        //1.判断用户是否存在

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        System.out.println("###");
        //断言
        Assert.notNull(user,"用户名不存在");



        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){


            return Result.fail("密码错误！");
        }

        //2.给token
        String token = jwtUtils.generateToken(user.getId());


        response.setHeader("Authorization",token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");


        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );


    }


    // 退出
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }



}
