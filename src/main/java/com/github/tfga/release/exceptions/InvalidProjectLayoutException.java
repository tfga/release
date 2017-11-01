package com.github.tfga.release.exceptions;

import br.gov.mpdft.util.string.StringUtils;

public class InvalidProjectLayoutException extends MensagemUsuarioException
{
    public InvalidProjectLayoutException(String url)
    {
        super(StringUtils.F("Can't find 'trunk' or 'branches' in '%s'", url));
    }
}
