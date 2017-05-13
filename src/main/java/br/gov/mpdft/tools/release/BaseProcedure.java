package br.gov.mpdft.tools.release;

import java.util.List;

import br.gov.mpdft.tools.release.exceptions.UnsupportedPackagingException;
import br.gov.mpdft.tools.release.os.OS;

public abstract class BaseProcedure implements Procedure
{
    OS os;
    Pom pom;
    List<String> params;
    UndoStack undoStack;
    String closedVersion;
    String url;
    String projectName;
    String tagName;

    @Override
    public void init(OS os, Pom pom, List<String> params, UndoStack undoStack,
                     String closedVersion, String url, 
                     String projectName, String tagName)
    {
        this.os = os;
        this.pom = pom;
        this.params = params;
        this.undoStack = undoStack;
        this.closedVersion = closedVersion;
        this.url = url;
        this.projectName = projectName;
        this.tagName = tagName;
    }
    
    
    static String getMavenCommand(String packaging)
    {
             if (packaging.equalsIgnoreCase("war")) return "mvn clean package";
        else if (packaging.equalsIgnoreCase("jar")) return "mvn clean deploy";
        else
        {
            throw new UnsupportedPackagingException(packaging);
        }
    }
}
