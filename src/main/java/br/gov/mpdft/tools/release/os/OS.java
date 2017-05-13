package br.gov.mpdft.tools.release.os;

import br.gov.mpdft.tools.release.exceptions.SystemException;

public class OS
{
    public void system(String cmd)
    {
        System.out.printf(">>> %s\n", cmd);
        
        int r = LibC.system(cmd);
        
        if (r != 0)
            throw new SystemException(cmd);
    }
}
