package br.gov.mpdft.tools.release.exceptions;

public class UnsupportedPackagingException extends MensagemUsuarioException
{
    public UnsupportedPackagingException(String packaging)
    {
        super(String.format("Unsupported packaging: '%s'.\nSupported packaging values: 'war' and 'jar'.", packaging));
    }
}
