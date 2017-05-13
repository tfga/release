package br.gov.mpdft.tools.release;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import static org.mockito.Mockito.*;

import br.gov.mpdft.tools.release.confirm.ConfirmatorMock;
import br.gov.mpdft.tools.release.exceptions.AlreadyReleasedException;
import br.gov.mpdft.tools.release.exceptions.UnsupportedPackagingException;
import br.gov.mpdft.tools.release.os.OsMock;
import br.gov.mpdft.tools.release.os.Util;

import static br.gov.mpdft.tools.release.BaseProcedure.getMavenCommand;

import static br.gov.mpdft.tools.release.PomTest.*;


public class MainTest
{
    @Test
    public void realMain()
    {
        Main main = newMainMock();
        
        Pom pom = newPomMock(sourcePom);
        
        main.realMain(pom);
    }
    
    @Test
    public void realMain_with_extraArgs()
    {
        Main main = newMainMock();
        
        Pom pom = newPomMock(sourcePom);
        
        main.realMain(pom, new String[] { "-Pproducao" });
    }

    @Test
    public void realMain_packaging_jar()
    {
        OsMock osMock = new OsMock();
        osMock = spy(osMock);
        
        Main main = new Main(newSvnMock(), osMock, new ConfirmatorMock());
        
        Pom pom = newPomMock(pomJar);
        
        main.realMain(pom, new String[] { "-Pproducao" });
        
        verify(osMock).system("mvn clean deploy \"-Pproducao\"");
    }

    Main newMainMock()
    {
        return new Main(newSvnMock(), new OsMock(), new ConfirmatorMock());
    }
    
    @Test
    public void realMain_exception_after_closeVersion()
    {
        StandardProcedure proc = new StandardProcedure();
        proc = spy(proc);
        doThrow(new RuntimeException("fake")).when(proc).fakeExceptionPoint1();
        
        ProcedureFactory procFactory = mock(ProcedureFactory.class);
        when(procFactory.get(anyString())).thenReturn(proc);
        
        
        Main main = newMainMock();
        main.setProcedureFactory(procFactory);
        main = spy(main);
        
        main.realMain(newPomMock(sourcePom));
        
        verify(proc).closeVersionUndo(sourcePom);
    }

    @Test
    public void realMain_exception_after_createTag()
    {
        StandardProcedure proc = new StandardProcedure();
        proc = spy(proc);
        doThrow(new RuntimeException("fake")).when(proc).fakeExceptionPoint2();
        
        ProcedureFactory procFactory = mock(ProcedureFactory.class);
        when(procFactory.get(anyString())).thenReturn(proc);
        
        Main main = newMainMock();
        main.setProcedureFactory(procFactory);
        main = spy(main);

        main.realMain(newPomMock(sourcePom));
        
        verify(proc).closeVersionUndo(sourcePom);
        verify(proc).createTagUndo(anyString(), anyString());
    }
    
    @Test
    public void realMain_MensagemParaUsuarioException()
    {
        Main main = newMainMock();
        
        Pom pom = newPomMock(pomSemEncoding);
        
        main.realMain(pom);
    }
    
    @Test
    public void checkForSnapshotVersion()
    {
        Main main = newMainMock();
        
        try
        {
            main.checkForSnapshotVersion("1.24.0.0");
            
            fail("Devia ter lançado exceção.");
        }
        catch (AlreadyReleasedException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void joinAndQuote()
    {
        assertEquals("\"a\" \"b\" \"c\"", Util.joinAndQuote(Arrays.asList("a", "b", "c")));
    }
    
    @Test
    public void getMavenCommand_()
    {
        String packageCmd = "mvn clean package";
        String deployCmd  = "mvn clean deploy";
        
        assertEquals(packageCmd, getMavenCommand("war"));
        assertEquals(deployCmd,  getMavenCommand("jar"));
        
        // testando case-insensitiveness
        assertEquals(packageCmd, getMavenCommand("wAr"));
        assertEquals(deployCmd,  getMavenCommand("Jar"));
    }
    
    @Test
    public void getMavenCommand_unsupported_packaging()
    {
        try
        {
            getMavenCommand("pom");
            
            fail("Deveria ter lançado exceção.");
        }
        catch (UnsupportedPackagingException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    Pom newPomMock(String pom)
    {
        return new Pom(pom)
        {
            @Override
            public void save()
            {}
        };
    }

    Svn newSvnMock()
    {
        return new Svn()
        {
            @Override
            public void checkForLocalModifications()
            {}
            
            @Override
            public String getUrl(String wcPath)
            {
                String trunkThemis = "https://svn.mpdft.gov.br/svn/repo/sistemas/ativos/SisproWeb/trunk/Fontes";

                return trunkThemis;
            }
        };
    }
}
