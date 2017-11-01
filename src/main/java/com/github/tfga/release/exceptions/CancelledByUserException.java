package com.github.tfga.release.exceptions;

public class CancelledByUserException extends MensagemUsuarioException
{
    public CancelledByUserException()
    {
        super("Cancelado pelo usuário.");
    }
}
