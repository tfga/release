package com.github.tfga.release.exceptions;

public class AlreadyReleasedException extends MensagemUsuarioException
{
    public AlreadyReleasedException()
    {
        super("A versão do projeto não é SNAPSHOT.\nPq diabos vc está tentando fechar um projeto já fechado?");
    }
}
