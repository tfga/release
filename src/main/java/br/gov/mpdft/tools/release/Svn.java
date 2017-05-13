package br.gov.mpdft.tools.release;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.gov.mpdft.tools.release.exceptions.LocalModificationsException;
import br.gov.mpdft.tools.release.os.Util;
import br.gov.mpdft.util.string.StringUtils;


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
