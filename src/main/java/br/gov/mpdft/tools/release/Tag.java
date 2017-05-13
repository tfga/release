package br.gov.mpdft.tools.release;
import static java.util.Arrays.asList;

import java.util.Arrays;

import br.gov.mpdft.tools.release.exceptions.InvalidProjectLayoutException;
import br.gov.mpdft.util.list.Predicate;
import static br.gov.mpdft.util.string.StringUtils.*;


public class Tag
{
    public String getBaseUrl(String url)
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
        
        pathElems[i] = "tags";
        
        // Despreza tudo o que vem depois
        pathElems = copyUpTo(pathElems, i);
        
        return toStringEntreVirgulas(asList(pathElems), "/");
    }

    /**
     * Devolve uma cópia da array, só até a[i] (inclusive)
     * 
     * @param <T>
     * @param array
     * @param i
     * @return
     */
    <T> T[] copyUpTo(T[] array, int i)
    {
        return Arrays.copyOf(array, i + 1);
    }

    /**
     * Retorna o índice do último 'elem' em 'array'
     * ou -1 caso não encontre. 
     * 
     * TODO mover para ListUtils?
     * 
     */
    <T> int rindex(T[] array, T wanted)
    {
        for(int i = array.length - 1; i > 0; i--)
        {
            T elem = array[i];
            
            if (elem.equals(wanted))
                return i;
        }
        
        return -1;
    }

    /**
     * Retorna o índice do último elemento de 'array' que satisfaz 'pred'
     * ou -1 caso não encontre. 
     * 
     * TODO mover para ListUtils?
     * 
     */
    <T> int rindex(T[] array, Predicate<T> pred)
    {
        for(int i = array.length - 1; i > 0; i--)
        {
            T elem = array[i];
            
            if (pred.predicate(elem))
                return i;
        }
        
        return -1;
    }
    
    public String getTagName(String projectName, String version)
    {
        return String.format("%s-%s", projectName, version);
    }

    public String getTagTarget(String url, String projectName, String version)
    {
        return String.format("%s/%s", getBaseUrl(url), getTagName(projectName, version));
    }
}
