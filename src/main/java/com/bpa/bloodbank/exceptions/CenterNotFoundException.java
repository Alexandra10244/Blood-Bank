package com.bpa.bloodbank.exceptions;

public class CenterNotFoundException extends RuntimeException{
    public CenterNotFoundException(String message){
        super(message);
    }
}
