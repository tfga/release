package br.gov.mpdft.tools.release.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.gov.mpdft.util.string.Formatter;
import br.gov.mpdft.util.string.StringUtils;


public class Util
{
    public static String popen(String ... tokens)
    {
        List<String> lines = popenAsList(tokens);
        
        return joinLines(lines);
    }
    
    /**
     * @param tokens
     * @return 
     * @throws IOException 
     */
    public static List<String> popenAsList(String ... tokens)
    {
        try
        {
            Process process = Runtime.getRuntime().exec(tokens);
            
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            
            List<String> lines = new LinkedList<String>();
            
            while (true)
            {
                String line = inputReader.readLine();
                
                if (line == null)
                    break;
                
                lines.add(line);
            }
            
            int status = process.waitFor();
            if (status != 0)
            {
                System.out.println(status);
                
                // Vamos pegar o que foi cuspido em stderr pra colocar como msg da excessão
                StringBuilder errorMsg = new StringBuilder();
                while (true)
                {
                    int c = errorReader.read();
                    
                    if (c == -1)
                        break;
                    
                    errorMsg.append((char)c);
                }
                
                throw new RuntimeException(errorMsg.toString());
            }
            
            return lines;
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(StringUtils.F("Processo interrompido: '%s'", joinTokens(tokens)), e);
        }
        catch (IOException e)
        {
//            e.printStackTrace();
            
            throw new RuntimeException(StringUtils.F("Erro ao executar '%s'", joinTokens(tokens)), e);
        }
    }
    
    static String joinTokens(String... tokens)
    {
        return StringUtils.toStringEntreVirgulas(Arrays.asList(tokens), " ");
    }
    
    public static String joinLines(List<String> lines)
    {
        return StringUtils.toStringEntreVirgulas(lines, "\n");
    }

    static <T> void print(T[] tokens)
    {
        String line = StringUtils.toStringEntreVirgulas(Arrays.asList(tokens), " ");
        System.out.println(line);
    }
    
    <T> void printLines(List<T> lines)
    {
        for (T line : lines)
        {
            System.out.println(line);
        }
    }
    
    static <T> void printLines(T[] lines)
    {
        for (T line : lines)
        {
            System.out.println(line);
        }
    }
    
    static String quote(String s)
    {
        /*
         * Tem que ser com double quotes: no Windows só funciona assim.
         */
        return String.format("\"%s\"", s);
    }
    
    public static String joinAndQuote(List<String> args)
    {
        return StringUtils.toStringEntreVirgulas(args, " ", new Formatter<String>()
        {
            @Override
            public String format(String s)
            {
                return quote(s);
            }
        });
    }
}
