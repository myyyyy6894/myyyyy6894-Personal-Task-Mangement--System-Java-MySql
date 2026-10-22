package com.example.ptms.controller;

import com.example.ptms.common.Result;
import com.example.ptms.dto.UserInfoResponse;
import com.example.ptms.entity.User;
import com.example.ptms.interceptor.UserContext;
import com.example.ptms.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/user/me")
    public Result<UserInfoResponse> me() {
        Long userId = UserContext.get();
        User user = userService.getById(userId);
        UserInfoResponse data = new UserInfoResponse(
                user.getId(), user.getUsername(), user.getNickname(),
                user.getCreatedAt());
        return Result.ok(data);

    }
}
