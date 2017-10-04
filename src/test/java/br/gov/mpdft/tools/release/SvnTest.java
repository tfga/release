package br.gov.mpdft.tools.release;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import br.gov.mpdft.tools.release.Svn;
import br.gov.mpdft.tools.release.exceptions.InvalidProjectLayoutException;
import br.gov.mpdft.tools.release.exceptions.LocalModificationsException;


public class SvnTest
{
    String urlThemis = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/trunk/Fontes";

    String svnInfo = String.format(""
        + " Path: ../Themis\n"
        + " URL: %s\n"
        + " Repository Root: https://svn.mpdft.gov.br/svn/repo\n"
        + " Repository UUID: efc1a043-6ae7-4d3e-b37c-6746b09e9311\n"
        + " Revision: 17259\n"
        + " Node Kind: directory\n"
        + " Schedule: normal\n"
        + " Last Changed Author: pericles.junior\n"
        + " Last Changed Rev: 17259\n"
        + " Last Changed Date: 2013-01-17 17:04:37 -0200 (Thu, 17 Jan 2013)\n"
        , urlThemis);
    
    
    @Test
    public void getUrlFromSvnInfo()
    {
        Svn svn = new Svn();
        
        String url = svn.getUrlFromSvnInfo(svnInfo);
        
        assertEquals(urlThemis, url);
    }
    
    // @Test
    public void getUrl()
    {
        Svn svn = new Svn();
        
        assertEquals(urlThemis, svn.getUrl("../Themis/"));
    }
    
    @Test
    @Ignore // only worked when the code was in SVN
            // *and* the WC was dirty
    public void checkForLocalModifications()
    {
        try
        {
            Svn svn = new Svn();
        
            svn.checkForLocalModifications();
            
            fail("Deveria ter lançado exceção");
        }
        catch (LocalModificationsException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
