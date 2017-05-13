package br.gov.mpdft.tools.release;

import static br.gov.mpdft.util.string.StringUtils.toStringEntreVirgulas;
import static java.util.Arrays.asList;

import java.util.Arrays;

import br.gov.mpdft.tools.release.exceptions.InvalidProjectLayoutException;
import br.gov.mpdft.util.list.Predicate;

public class NewLayoutTag extends Tag
{
    public String getTagSource(String url)
    {
        String[] pathElems = url.split("/");
        
        // acha a última ocorrência de "trunk" ou "branches"
        int i = rindex(pathElems, new Predicate<String>()
        {
            @Override
            public boolean predicate(String s)
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

        return toStringEntreVirgulas(asList(pathElems), "/");
    }
}
