package br.com.rodrigoplopesdev.calendula_api.exceptions;

public class DuplicatedTitleException extends RuntimeException {
    
    public DuplicatedTitleException(String mensagem){
        super(mensagem);
    }
}
