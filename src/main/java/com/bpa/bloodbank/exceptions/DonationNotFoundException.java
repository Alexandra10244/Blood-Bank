package com.bpa.bloodbank.exceptions;

public class DonationNotFoundException extends RuntimeException{
    public DonationNotFoundException(String message) {
        super(message);
    }
}
