package br.gov.mpdft.tools.release.confirm;

import static org.junit.Assert.*;

import org.junit.Test;


public class SwingConfirmatorTest
{
    // @Test
    /* 
     * TODO: use Fest Assertions
     * 
     * Esc   => CancelledByUserException
     * Enter => tagUrl == "tagUrl"
     * 
     */
    public void confirm()
    {
        SwingConfirmator confirmator = new SwingConfirmator();
        
        String tagUrl = confirmator.confirm("Temis-1.24.0.0", "1.24.0.1");
        
        System.out.printf("-%s-", tagUrl);
    }
}
