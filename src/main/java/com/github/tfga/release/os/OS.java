package com.github.tfga.release.os;

import com.github.tfga.release.exceptions.SystemException;

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
