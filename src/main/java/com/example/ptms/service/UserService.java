package com.example.ptms.service;

import com.example.ptms.common.BusinessException;
import com.example.ptms.dto.LoginRequest;
import com.example.ptms.dto.RegisterRequest;
import com.example.ptms.entity.User;
import com.example.ptms.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public User getById(Long id){
        return userMapper.findById(id);
    }

    public User getByUsername(String username){
        return userMapper.findByUsername(username);
    }

    public void register(RegisterRequest req){
        User exist = userMapper.findByUsername(req.getUsername());
        if(exist != null){
            throw new BusinessException(409,"用户名已经被注册");

        }
        String hash = passwordEncoder.encode(req.getPassword());

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(hash);
        user.setNickname(req.getNickname());

        userMapper.insert(user);
    }

    public User login(LoginRequest req){
        User user = userMapper.findByUsername(req.getUsername());

        if(user == null || !passwordEncoder.matches(req.getPassword(),user.getPassword())){
            throw new BusinessException(401 , "用户名或密码错误");
        }
        return user;
    }


}
