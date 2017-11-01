package com.github.tfga.release.exceptions;

public class SystemException extends MensagemUsuarioException
{
    public SystemException(String cmd)
    {
        super(String.format("Failed: '%s'", cmd));
    }
}
