package com.github.tfga.release;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.tfga.release.exceptions.LocalModificationsException;
import com.github.tfga.release.os.Util;


public class Svn
{
    Pattern urlRegEx = Pattern.compile("URL: (.+)");

    String getUrlFromSvnInfo(String svnInfo)
    {
        Matcher m = urlRegEx.matcher(svnInfo);
        if (m.find())
        {
//            System.out.println(m.group(0));
//            System.out.println(m.group(1) + '|' + m.group(2));
            
            return m.group(1);
        }
        else
            return null;
    }

    public String getUrl(String wcPath)
    {
        String svnInfo = Util.popen("svn", "info", wcPath);
        
        return getUrlFromSvnInfo(svnInfo);
    }

    public void checkForLocalModifications()
    {
        String svnSt = Util.popen("svn", "st");
        
        if (!svnSt.isEmpty())
            throw new LocalModificationsException(svnSt);
    }
}
