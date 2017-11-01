package com.github.tfga.release.exceptions;

public class MensagemUsuarioException extends RuntimeException
{
    public MensagemUsuarioException()
    {}
    
    public MensagemUsuarioException(String message)
    {
        super(message);
    }
}
