package com.jh.exception;

public class UserNotExistsException extends Exception
{
    public UserNotExistsException() {
        super("user not exist");
    }
}
