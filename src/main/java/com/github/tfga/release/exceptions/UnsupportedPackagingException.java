package com.github.tfga.release.exceptions;

public class UnsupportedPackagingException extends MensagemUsuarioException
{
    public UnsupportedPackagingException(String packaging)
    {
        super(String.format("Unsupported packaging: '%s'.\nSupported packaging values: 'war' and 'jar'.", packaging));
    }
}
