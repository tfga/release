package br.gov.mpdft.tools.release;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import br.gov.mpdft.tools.release.exceptions.PomSemEncodingException;
import br.gov.mpdft.tools.release.exceptions.SnapshotDependenciesException;
import br.gov.mpdft.tools.release.os.LibC;


public class PomTest
{
    static String testResource(String filename)
    {
        return "src/test/resources/" + filename;
    }
    
    static String sourcePom          = testResource("pom.xml");
    static String targetPom          = testResource("pomNew.xml");
    static String pomComSnapshots    = testResource("pom.SNAPSHOTs.xml");
    static String pomIlegal          = testResource("pom.ilegal.xml");
    static String pomComSuperPom     = testResource("pom.SuperPom.xml");
    static String pomSemEncoding     = testResource("pom.NoEncoding.xml");
    static String pomSemDependencias = testResource("pom.NoDeps.xml");
    static String pomJar             = testResource("pom.NoDeps.xml");
    static String pomBOMComSnapshots = testResource("pom.SNAPSHOTs.bom.xml");
    static String pomBOM             = testResource("pom.bom.xml");

//    @Test
//    public void fileExists()
//    {
//        File file = new File(sourcePom);
//        
//        assertTrue(file.exists());
//    }
//    
//    @Test
//    public void preserveFileFormatting_string()
//    {
//        String xml = "<hello-world />";
//        
//        XMLParser parser = new XMLParser ();
//        Document doc = parser.parse (new XMLStringSource (xml));
//        assertEquals (xml, doc.toXML ());
//    }
    
    @Test
    public void preserveFileFormatting() throws IOException
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();

        pom.save();
        
        //  Os dois arquivos devem ser iguais
        assertEquals(0, LibC.system(String.format("diff -q '%s' '%s'", sourcePom, targetPom)));
    }
    
    @Test
    public void pomSemEncoding()
    {
        try
        {
            Pom pom = new Pom(pomSemEncoding);
            
            pom.read();
            
            Assert.fail("Deveria ter lan�ado exce��o.");
        }
        catch (PomSemEncodingException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void getPackaging()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();
        
        assertEquals("war", pom.getPackaging());
    }
    
    @Test
    public void getArtifactId()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();
        
        assertEquals("TemisArtifactId", pom.getArtifactId());
    }
    
    @Test
    public void getArtifactId_SuperPom()
    {
        Pom pom = new Pom(pomComSuperPom);
        
        pom.read();

        assertEquals("TemisArtifactId", pom.getArtifactId());
    }
    
    @Test
    public void getProjectVersion()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();
        
        assertEquals("1.24.0.0-SNAPSHOT", pom.getProjectVersion());
    }
    
    @Test
    public void getProjectVersion_SuperPom()
    {
        Pom pom = new Pom(pomComSuperPom);
        
        pom.read();
        
        assertEquals("1.24.0.0-SNAPSHOT", pom.getProjectVersion());
    }
    
    @Test
    public void setProjectVersion()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();
        
        final String NEW_VERSION = "2.67";
        
        assertFalse(pom.getProjectVersion().equals(NEW_VERSION));
        
        pom.setProjectVersion(NEW_VERSION);
        
        assertTrue(pom.getProjectVersion().equals(NEW_VERSION));
    }
    
//    @Test
//    desabilitado pq altera o pom.xml
    public void changeProjectVersionAndSave()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.setProjectVersion("1.24.0.0");
        
        pom.save();
    }

    @Test
    public void checkForSnapshotDeps_com()
    {
        try
        {
            Pom pom = new Pom(pomComSnapshots);
            
            pom.read();
            
            pom.checkForSnapshotDeps();
            
            Assert.fail("Devia ter lan�ado exce��o");
        }
        catch (SnapshotDependenciesException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void checkForSnapshotDeps_sem()
    {
        Pom pom = new Pom(sourcePom);
        
        pom.read();
        
        // N�o deve lan�ar exce��o
        pom.checkForSnapshotDeps();
    }
    
    @Test
    public void pomSemDependencias()
    {
        Pom pom = new Pom(pomSemDependencias);
        
        pom.read();
        
        pom.checkForSnapshotDeps();
    }
    
    @Test
    public void closeVersion()
    {
        assertEquals("1.24.0.0", Pom.closeVersion("1.24.0.0-SNAPSHOT"));
    }

    @Test
    public void incVersion()
    {
        assertEquals("1.24.0.1", Pom.incVersion("1.24.0.0"));
    }

	@Test(expected = SnapshotDependenciesException.class)
	public void pomUsandoBOMComSnapshot() {
		Pom pom = new Pom(pomBOMComSnapshots);

		pom.read();

		pom.checkForSnapshotDeps();
	}

	@Test
	public void pomUsandoBOM() {
		Pom pom = new Pom(pomBOM);

		pom.read();

		pom.checkForSnapshotDeps();
	}

}
