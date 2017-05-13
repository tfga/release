package br.gov.mpdft.tools.release.exceptions;

public class CancelledByUserException extends MensagemUsuarioException
{
    public CancelledByUserException()
    {
        super("Cancelado pelo usuário.");
    }
}
