package com.github.tfga.release;

import com.github.tfga.release.os.OS;

public class ProcUtil
{
    static void commitVersionChange(OS os, final Pom pom, String oldVersion, String newVersion)
    {
        // Commit
        os.system(String.format("svn commit %s -m \"[release] %s => %s\"",
                                pom.getFilename(), 
                                oldVersion,
                                newVersion
        ));
    }
    
    static void revertVersionChange(OS os, final Pom pom, String oldVersion, String newVersion)
    {
        // Commit
        os.system(String.format("svn commit %s -m \"[release] revertendo: %s => %s\"",
                                pom.getFilename(), 
                                newVersion,
                                oldVersion
        ));
    }
}
