package com.github.tfga.release.exceptions;

import static java.lang.String.format;

public class InvalidProjectLayoutException extends MensagemUsuarioException
{
    public InvalidProjectLayoutException(String url)
    {
        super(format("Can't find 'trunk' or 'branches' in '%s'", url));
    }
}
