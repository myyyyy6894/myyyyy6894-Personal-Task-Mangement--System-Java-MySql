package com.example.ptms.controller;

import com.example.ptms.common.JwtUtil;
import com.example.ptms.common.Result;
import com.example.ptms.dto.LoginRequest;
import com.example.ptms.dto.RegisterRequest;
import com.example.ptms.entity.User;
import com.example.ptms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;

    private final JwtUtil jwtUtil;
    public AuthController(UserService userService,JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/api/auth/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return Result.ok(null);
    }

    @PostMapping("api/auth/login")
    public Result<Map<String,Object>> login(@Valid @RequestBody LoginRequest req){
        User user = userService.login(req);
        String token = jwtUtil.generate(user.getId(),user.getUsername());

        Map<String,Object> data = new HashMap<>();
        data.put("token",token);
        data.put("nickname",user.getNickname());

        return Result.ok(data);
    }

}