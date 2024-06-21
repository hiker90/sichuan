package com.jh.exception;

public class UserPasswordNotMatchException extends Exception
{
    public UserPasswordNotMatchException() {
        super("user password not match");
    }
}
