package br.gov.mpdft.tools.release;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class NewLayoutTagTest
{
    @Test
    public void isEstruturaNova()
    {
        ProcedureFactory procFactory = new ProcedureFactory();
        
        assertTrue( procFactory.isEstruturaNova("https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk/desenvolvimento"));
        assertTrue(!procFactory.isEstruturaNova("https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk/"));
        
        assertTrue( procFactory.isEstruturaNova("https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/branches/eGab-0.1.0.0/desenvolvimento"));
    }
    
    @Test
    public void getTagSource_trunk()
    {
        NewLayoutTag tag = new NewLayoutTag();
        
        String trunk = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk/desenvolvimento";
        assertEquals(  "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk", tag.getTagSource(trunk));
    }
    
    @Test
    public void getTagSource_branch()
    {
        NewLayoutTag tag = new NewLayoutTag();
        
        String branch = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/branches/eGab-0.1.0.0/desenvolvimento";
        assertEquals(   "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/branches/eGab-0.1.0.0", tag.getTagSource(branch));
    }
    
    @Test
    public void getTagTarget_trunk()
    {
        Tag tag = new NewLayoutTag();
        
        String trunk = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk/desenvolvimento";
        assertEquals(  "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/tags/eGab-1.1.0.0", tag.getTagTarget(trunk, "eGab", "1.1.0.0"));
    }
    
    @Test
    public void getTagTarget_branch()
    {
        Tag tag = new NewLayoutTag();
        
        String branch = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/branches/eGab-0.1.0.0/desenvolvimento";
        assertEquals(   "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/tags/eGab-1.1.0.0", tag.getTagTarget(branch, "eGab", "1.1.0.0"));
    }
    
    @Test
    public void getTagCommand_trunk()
    {
        NewLayoutProcedure proc = new NewLayoutProcedure();
        
        String trunk = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/trunk/desenvolvimento";
        
        String tagCommand = proc.getTagCommand(trunk, "eGab", "1.1.0.0");
        
        System.out.println(tagCommand);
    }
    
    @Test
    public void getTagCommand_branch()
    {
        NewLayoutProcedure proc = new NewLayoutProcedure();
        
        String url = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/eGab/branches/eGab-0.1.0.0/desenvolvimento";
        
        String tagCommand = proc.getTagCommand(url, "eGab", "1.1.0.0");
        
        System.out.println(tagCommand);
    }
}
