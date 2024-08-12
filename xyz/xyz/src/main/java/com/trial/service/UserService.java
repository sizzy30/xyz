package com.trial.service;

import com.trial.payload.LoginDto;
import com.trial.payload.UserDto;

public interface UserService {
    UserDto create(UserDto dto);

    String login(LoginDto loginDto);
}
