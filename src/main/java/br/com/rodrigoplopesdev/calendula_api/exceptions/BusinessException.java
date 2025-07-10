package br.com.rodrigoplopesdev.calendula_api.exceptions;

public class BusinessException extends RuntimeException{


    public BusinessException(String message){
        super(message);
    }
}
