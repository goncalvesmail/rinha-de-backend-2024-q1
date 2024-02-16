package com.goncalvesmail.rinhabackend.exception;

import java.security.SecureRandom;

public class NegocioException extends Exception {

    public NegocioException(String message) {
        super(message);
    }
}
