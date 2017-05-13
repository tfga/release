package br.gov.mpdft.tools.release;

import static br.gov.mpdft.tools.release.os.Util.joinAndQuote;

import java.util.List;

import br.gov.mpdft.util.Callback0;

public class NewLayoutProcedure extends BaseProcedure
{
    @Override
    public void exec()
    {
        String tagUrl = new NewLayoutTag().getTagTarget(url, projectName, closedVersion);
        
        String buildCommand = getMavenCommand(pom.getPackaging());
        
        final String oldVersion = pom.getProjectVersion();
        
        
        // Fecha a versão no pom
        pom.setProjectVersion(closedVersion);
        pom.save();
        
        undoStack.push(new Callback0()
        {
            @Override
            public void execute()
            {
                revert(pom.getFilename());
            }
        });
        
        // Package (war) ou deploy (jar)
        build(buildCommand, params);
        
        // Commit
        ProcUtil.commitVersionChange(os, pom, oldVersion, closedVersion);
        
        undoStack.push(new Callback0()
        {
            @Override
            public void execute()
            {
                // Muda a versão de volta
                pom.setProjectVersion(oldVersion);
                pom.save();

                ProcUtil.revertVersionChange(os, pom, oldVersion, closedVersion);
            }
        });
        
        fakeExceptionPoint1(); // aqui?
        
        // Create tag
        createTag(url, projectName, closedVersion);
        
        final String finalTagUrl = tagUrl;
        undoStack.push(new Callback0()
        {
            @Override
            public void execute()
            {
                rmTag(tagName, finalTagUrl);
            }
        });
        
        fakeExceptionPoint2();
    }

    void createTag(String url, String projectName, String closedVersion)
    {
        os.system(getTagCommand(url, projectName, closedVersion));
    }

    String getTagCommand(String url, String projectName, String closedVersion)
    {
        NewLayoutTag tag = new NewLayoutTag();
        
        String sourceUrl = tag.getTagSource(url);
        String targetUrl = tag.getTagTarget(url, projectName, closedVersion);
        String tagName   = tag.getTagName(projectName, closedVersion);
        
        return String.format("svn cp %s %s -m \"[release] Creating tag '%s'\"", sourceUrl, targetUrl, tagName);
    }

    void build(String buildCommand, List<String> params)
    {
        os.system(String.format("%s %s", buildCommand, joinAndQuote(params)));
    }
    
    void fakeExceptionPoint1()
    {}

    void fakeExceptionPoint2()
    {}

    void revert(String filename)
    {
        os.system(String.format("svn revert %s", filename));
    }
    
    void rmTag(String tagName, String tagUrl)
    {
        os.system(String.format("svn rm %s -m \"[release] Rollback: deleting tag '%s'\"", tagUrl, tagName));
    }
}
