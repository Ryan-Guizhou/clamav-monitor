package com.clamav.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clamav.backend.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}