package com.github.tfga.release.exceptions;


public class LocalModificationsException extends MensagemUsuarioException
{
    public LocalModificationsException(String svnSt)
    {
        super("Working copy has local modifications:\n\n" + svnSt);
    }
}
