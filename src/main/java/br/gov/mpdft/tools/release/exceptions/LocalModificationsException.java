package br.gov.mpdft.tools.release.exceptions;


public class LocalModificationsException extends MensagemUsuarioException
{
    public LocalModificationsException(String svnSt)
    {
        super("Working copy has local modifications:\n\n" + svnSt);
    }
}
