package br.gov.mpdft.tools.release;
import static org.junit.Assert.*;

import org.junit.Test;

import br.gov.mpdft.tools.release.Tag;
import br.gov.mpdft.tools.release.exceptions.InvalidProjectLayoutException;


public class TagTest
{
    @Test
    public void getTagsBaseUrl()
    {
        String trunkThemis = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/trunk/Fontes";
        String tagsThemis  = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/tags";
        
        Tag tag = new Tag();
        
        assertEquals(tagsThemis, tag.getBaseUrl(trunkThemis)); 
    }
    
    @Test
    public void getTagsBaseUrl_multiple_trunks()
    {
        String trunkThemis = "https://svn.mpdft.gov.br/svn/trunk/sistemas/ativos/SisproWeb/trunk/Fontes";
        String tagsThemis  = "https://svn.mpdft.gov.br/svn/trunk/sistemas/ativos/SisproWeb/tags";
        
        Tag tag = new Tag();
        
        assertEquals(tagsThemis, tag.getBaseUrl(trunkThemis)); 
    }
    
    @Test
    public void getTagsBaseUrl_branch()
    {
        String branchThemis = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/branches/Temis-0.19.11.1/";
        String tagsThemis   = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/tags";
        
        Tag tag = new Tag();
        
        assertEquals(tagsThemis, tag.getBaseUrl(branchThemis)); 
    }
    
    @Test
    public void getTagsBaseUrl_no_trunk()
    {
        String noTrunkUrl = "https://svn.mpdft.gov.br/svn/repo/Disi/Infra/tools/PyRelease";
            
        try
        {
            Tag tag = new Tag();
   
            tag.getBaseUrl(noTrunkUrl);
            
            fail("Deveria ter lançado exceção");
        }
        catch (InvalidProjectLayoutException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void getTagName()
    {
        Tag tag = new Tag();
        
        assertEquals("Temis-1.24.0.0", tag.getTagName("Temis", "1.24.0.0"));
    }
    
    @Test
    public void getTagUrl()
    {
        String trunkThemis = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/trunk/Fontes";
        
        Tag tag = new Tag();
        
        assertEquals("https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/tags/Temis-1.24.0.0", tag.getTagTarget(trunkThemis, "Temis", "1.24.0.0"));
    }
}
