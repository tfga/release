package com.github.tfga.release;

public class ProcedureFactory
{
    public boolean isEstruturaNova(String url)
    {
        String[] pathElems = url.split("/");
        
        int lastIndex = pathElems.length - 1;
        
        return pathElems[lastIndex].equals("desenvolvimento");
    }

    public Procedure get(String url)
    {
        return isEstruturaNova(url) ? new NewLayoutProcedure()
                                    : new StandardProcedure()
                                    ;
    }
}
