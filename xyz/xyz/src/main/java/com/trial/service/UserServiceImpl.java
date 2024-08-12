package com.trial.service;

import com.trial.UserRepository.UserRepository;
import com.trial.entity.User;
import com.trial.exception.UserExists;
import com.trial.payload.LoginDto;
import com.trial.payload.UserDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
private JwtService jwtService;
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto create(UserDto dto) {
        User user =maptoEntity(dto);
        Optional<User> opEmail=userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email exixts");
        }
        Optional<User> opUsername=userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("Username exists");
        }
        String pass= BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(6));
        user.setPassword(pass);
        User user1=userRepository.save(user);
        UserDto resdto=maptoDto(user1);
        return resdto;
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<User> op=userRepository.findByEmailOrUsername(loginDto.getUsername(),loginDto.getUsername());
        if(op.isPresent()){
            User user= op.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                return jwtService.generateToken(user);
            }
        }
        return null;
    }

    private UserDto maptoDto(User user) {
        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    private User maptoEntity(UserDto dto) {
        User user=new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}
