package com.kingmj.qr_login.exception;

public class QRGenerationException extends RuntimeException{
    public QRGenerationException(String message, Throwable cause){
        super(message, cause);
    }
}
