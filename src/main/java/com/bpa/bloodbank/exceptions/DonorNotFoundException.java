package com.bpa.bloodbank.exceptions;

public class DonorNotFoundException extends RuntimeException{
    public DonorNotFoundException(String message){
        super(message);
    }
}
