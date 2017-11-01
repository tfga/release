package com.github.tfga.release;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.tfga.release.exceptions.SystemException;
import com.github.tfga.release.os.OS;


public class OsTest
{
    @Test
    public void system_prog_inexistente()
    {
        try
        {
            OS os = new OS();
            
            os.system("shit");
            
            fail("Devia ter lançado exceção");
        }
        catch (SystemException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void system_prog_falha()
    {
        try
        {
            OS os = new OS();
            
            os.system("ls shitos");
        }
        catch (SystemException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    // @Test
    public void system_arg_entre_aspas()
    {
        OS os = new OS();

        os.system("./printArgs.py 'a b'");   // Unix
        /*
         * Deve imprimir:
         * 
         *     './printArgs.py'
               'a b'
         */
           
//        os.system("printArgs.bat 'a b'");  // Windows
        /*
         * Saída:
         * 
               -'a- -b'-
           
           O Windows não reconhece single quotes; vc tem que usar aspas duplas...

         */
    }
}
