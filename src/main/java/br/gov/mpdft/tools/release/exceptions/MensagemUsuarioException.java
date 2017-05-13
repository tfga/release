package br.gov.mpdft.tools.release.exceptions;

public class MensagemUsuarioException extends RuntimeException
{
    public MensagemUsuarioException()
    {}
    
    public MensagemUsuarioException(String message)
    {
        super(message);
    }
}
