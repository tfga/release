package com.github.tfga.release;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import com.github.tfga.release.exceptions.PomSemEncodingException;
import com.github.tfga.release.exceptions.SnapshotDependenciesException;

import br.gov.mpdft.util.list.ListUtils;
import br.gov.mpdft.util.list.Predicate;
import br.gov.mpdft.util.string.StringUtils;
import de.pdark.decentxml.Document;
import de.pdark.decentxml.Element;
import de.pdark.decentxml.XMLParser;
import de.pdark.decentxml.XMLWriter;

public class Pom
{
    String filename;
    Document doc;

    public Pom(String filename)
    {
        this.filename = filename;
    }
    
    void read()
    {
        this.doc = read(filename);
        
        if (doc.getEncoding() == null)
            throw new PomSemEncodingException();
    }
    
    Document read(String filename)
    {
        try
        {
            return XMLParser.parse(new File(filename));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void save()
    {
        save(filename);
    }

    public void save(String filename)
    {
        try
        {
            // Tenho que fazer isso pq
            // sen�o ele converte para ISO-xxxx-1
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), doc.getEncoding());
            
            doc.toXML(new XMLWriter(writer));
            
            writer.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public String getPackaging()
    {
        return doc.getChild("project/packaging").getTrimmedText();
    }
    
    public String getArtifactId()
    {
        return doc.getChild("project/artifactId").getTrimmedText();
    }

    public String getProjectVersion()
    {
        return doc.getChild("project/version").getTrimmedText();
    }

    public void setProjectVersion(String newVersion)
    {
        doc.getChild("project/version").setText(newVersion);
    }

    public void checkForSnapshotDeps()
    {
        boolean containsBOM = checkForSnapshotDepsFromPath("project/dependencyManagement/dependencies", false);
        checkForSnapshotDepsFromPath("project/dependencies", containsBOM);
    }

    private boolean checkForSnapshotDepsFromPath(String depPath, final boolean allowsEmptyVersion) {
        Element dependeciesElem = doc.getChild(depPath);

        boolean constainsDeps = dependeciesElem != null;

        if (constainsDeps) {
            List<Element> snapshotDeps = ListUtils.filter(dependeciesElem.getChildren(), new Predicate<Element>()
            {
                @Override
                public boolean predicate(Element dep)
                {
                    return isSnapshotDependency(dep, allowsEmptyVersion);
                }
            });

            if (!snapshotDeps.isEmpty())
                throw new SnapshotDependenciesException(snapshotDeps);
        }

        return constainsDeps;
    }
    
    public static String formatDependency(Element dependency)
    {
        String artifactId = dependency.getChild("artifactId").getTrimmedText();
        String version    = dependency.getChild("version").getTrimmedText();
        
        return String.format("%s-%s", artifactId, version);
    }

    boolean isSnapshotDependency(Element dependency, boolean allowEmptyVersion)
    {
        Element versionElem = dependency.getChild("version");

        if (versionElem == null && allowEmptyVersion) {
            return false;
        }

        String version = versionElem.getTrimmedText();

        return isSnapshotVersion(version);
    }

    static boolean isSnapshotVersion(String version)
    {
        return version.contains("SNAPSHOT");
    }

    public static String closeVersion(String version)
    {
        return version.replace("-SNAPSHOT", "");
    }

    public String getFilename()
    {
        return filename;
    }

    public static String incVersion(String version)
    {
        String[] numbers = version.split("\\.");
        
        int last = numbers.length - 1;
        
        numbers[last] = incIntString(numbers[last]);
        
        return StringUtils.toStringEntreVirgulas(Arrays.asList(numbers), ".");
    }

    static String incIntString(String lastNumber)
    {
        int lastNumberAsInt = Integer.parseInt(lastNumber);
        
        lastNumberAsInt++;
        
        return Integer.toString(lastNumberAsInt);
    }
}
