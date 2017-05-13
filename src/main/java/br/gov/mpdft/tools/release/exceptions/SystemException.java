package br.gov.mpdft.tools.release.exceptions;

public class SystemException extends MensagemUsuarioException
{
    public SystemException(String cmd)
    {
        super(String.format("Failed: '%s'", cmd));
    }
}
