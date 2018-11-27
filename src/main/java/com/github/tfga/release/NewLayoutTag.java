package com.github.tfga.release;

import static com.github.underscore.U.join;
import static java.util.Arrays.asList;

import com.github.tfga.release.exceptions.InvalidProjectLayoutException;
import com.github.underscore.Predicate;
import com.github.underscore.U;

public class NewLayoutTag extends Tag
{
    public String getTagSource(String url)
    {
        String[] pathElems = url.split("/");
        
        // acha a última ocorrência de "trunk" ou "branches"
        int i = rindex(pathElems, new Predicate<String>()
        {
            @Override
            public boolean test(String s)
            {
                return s.equals("trunk") || s.equals("branches");
            }
        });
        
        if (i == -1)
            throw new InvalidProjectLayoutException(url);

        
        
        if (pathElems[i].equals("trunk"))
        {
            pathElems = copyUpTo(pathElems, i);
        }
        else if (pathElems[i].equals("branches"))
        {
            pathElems = copyUpTo(pathElems, i + 1);
        }

        return join(asList(pathElems), "/");
    }
}
