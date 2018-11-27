package com.github.tfga.release;

import static com.github.tfga.release.os.Util.joinAndQuote;

import com.github.tfga.release.util.Callback0;

public class StandardProcedure extends BaseProcedure
{
    @Override
    public void exec()
    {
        String tagUrl = new Tag().getTagTarget(url, projectName, closedVersion);
        
        String buildCommand = getMavenCommand(pom.getPackaging());
        
        // Close version
        pom.setProjectVersion(closedVersion);
        pom.save();
        
        undoStack.push(new Callback0()
        {
            @Override
            public void execute()
            {
                closeVersionUndo(pom.getFilename());
            }
        });
        
        fakeExceptionPoint1();
        
//        # Create tag
//        system('svn cp . {tagUrl} -m "[release] Creating tag {tagName}"'.format(tagUrl=tagUrl, tagName=sQuote(tagName)))
//        undoStack.push(lambda: createTagUndo(tagName, tagUrl)) #createTagUndo)  # svn rm {tagUrl} -m "[release] rollback: deleting tag {tagName}"
//        fakeExceptionPoint2()
        
        // Create tag
        os.system(String.format("svn cp . %s -m \"[release] Creating tag '%s'\"", tagUrl, tagName));
        
        final String finalTagUrl = tagUrl;
        undoStack.push(new Callback0()
        {
            @Override
            public void execute()
            {
                createTagUndo(tagName, finalTagUrl);
            }
        });
        
        fakeExceptionPoint2();
        
        // Package (war) ou deploy (jar)
        os.system(String.format("%s %s", buildCommand, joinAndQuote(params)));

        
        /*
         *
        undoStack.push(lambda: closeVersionUndo(pom.getFilename())) #closeVersionUndo)  # svn revert {pomFilename}
        fakeExceptionPoint1()
        
        url = getUrl('.')
        
        projectName = pom.getArtifactId()
        
        tagName = getTagName(projectName, version)
        
        tagName = confirm(tagName)
        
        tagUrl = getTagUrl(getTagsBaseUrl(url), projectName, version)
        
        # Create tag
        system('svn cp . {tagUrl} -m "[release] Creating tag {tagName}"'.format(tagUrl=tagUrl, tagName=sQuote(tagName)))
        undoStack.push(lambda: createTagUndo(tagName, tagUrl)) #createTagUndo)  # svn rm {tagUrl} -m "[release] rollback: deleting tag {tagName}"
        fakeExceptionPoint2()
         */
    }
    
    void fakeExceptionPoint1()
    {}

    void fakeExceptionPoint2()
    {}

    void closeVersionUndo(String filename)
    {
        // system("svn revert {0}".format(pomFilename))
        
        os.system(String.format("svn revert %s", filename));
    }
    
    void createTagUndo(String tagName, String tagUrl)
    {
        // system('svn rm {tagUrl} -m "[release] Rollback: deleting tag {tagName}"'.format(tagUrl=tagUrl, tagName=sQuote(tagName)))
        os.system(String.format("svn rm %s -m \"[release] Rollback: deleting tag '%s'\"", tagUrl, tagName));
    }
}
