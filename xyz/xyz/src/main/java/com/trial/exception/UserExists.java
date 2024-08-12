package com.trial.exception;

public class UserExists extends RuntimeException{
    public UserExists(String msg){
        super(msg);
    }
}
