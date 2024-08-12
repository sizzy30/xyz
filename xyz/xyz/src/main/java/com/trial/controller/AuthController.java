package com.trial.controller;

import com.trial.payload.JwtToken;
import com.trial.payload.LoginDto;
import com.trial.payload.UserDto;
import com.trial.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto ){
        UserDto userDto=userService.create(dto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto ){
       String token= userService.login(loginDto);
        JwtToken jwtToken =new JwtToken();
        if(token!=null){
            jwtToken.setType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid",HttpStatus.UNAUTHORIZED);
    }
}
