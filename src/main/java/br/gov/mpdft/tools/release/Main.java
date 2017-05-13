package br.gov.mpdft.tools.release;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.gov.mpdft.tools.release.confirm.Confirmator;
import br.gov.mpdft.tools.release.confirm.RenameDialog;
import br.gov.mpdft.tools.release.confirm.SwingConfirmator;
import br.gov.mpdft.tools.release.exceptions.AlreadyReleasedException;
import br.gov.mpdft.tools.release.exceptions.MensagemUsuarioException;
import br.gov.mpdft.tools.release.exceptions.UnsupportedPackagingException;
import br.gov.mpdft.tools.release.os.OS;
import br.gov.mpdft.tools.release.os.OsMock;
import br.gov.mpdft.util.Callback0;
import br.gov.mpdft.util.string.Formatter;
import br.gov.mpdft.util.string.StringUtils;

import static br.gov.mpdft.tools.release.Pom.*;

public class Main
{
    private Svn svn;
    private OS os;
    private Confirmator confirmator;
    private ProcedureFactory procedureFactory;
    
    public Main(Svn svn, OS os, Confirmator confirmator)
    {
        this.svn = svn;
        this.os = os;
        this.confirmator = confirmator;
        this.procedureFactory = new ProcedureFactory();
    }
    
    public void setProcedureFactory(ProcedureFactory procedureFactory)
    {
        this.procedureFactory = procedureFactory;
    }

    /*
     * undoStack = UndoStack()
    
    try:
        checkForLocalModifications()
        
        version = pom.getProjectVersion()
        
        checkForSnapshotVersion(version)
        
        pom.checkForSnapshotDeps()
        
        # Close version
        version = closeVersion(version)
        pom.setProjectVersion(version)
        pom.save()
        
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
        
        # Package (war) ou deploy (jar)
        system('mvn clean package') # {0}'.format(extraArgs)) # e.g. -Pproducao
    
    except Exception as e:
        print '''
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                             FAILURE!
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
'''
        if isinstance(e, MensagemUsuarioException): 
            print e
        else:
            traceback.print_exc(e)
            
        undoStack.undoAll()
        
        return 1
    
    except KeyboardInterrupt:
        print '''
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                         KeyboardInterrupt!
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
'''
        undoStack.undoAll()
        
        return 1
        
    else:
        # Change to next SNAPSHOT version
        newVersion = incVersion(version) + '-SNAPSHOT'
        
        newVersion = confirm(newVersion)
        
        pom.setProjectVersion(newVersion)
        pom.save()
        
        # Commit
        system('svn commit {pom} -m "[release] {oldVersion} => {newVersion}"'.format(pom        = pom.getFilename(), 
                                                                                     oldVersion = version,
                                                                                     newVersion = newVersion,
                                                                                     ))
        
        return 0
     */
    int realMain(final Pom pom)
    {
        return realMain(pom, new String[0]);
    }
    
    int realMain(final Pom pom, String[] args)
    {
        return realMain(pom, Arrays.asList(args));
    }
    
    int realMain(final Pom pom, List<String> params)
    {
        UndoStack undoStack = new UndoStack();
        String closedVersion,
               nextVersion;
        
        try
        {
        /*
         *      * undoStack = UndoStack()
    
    try:
        checkForLocalModifications()
        
        version = pom.getProjectVersion()
        
        checkForSnapshotVersion(version)
        
        pom.checkForSnapshotDeps()
        
        # Close version
        version = closeVersion(version)
        pom.setProjectVersion(version)
        pom.save()

         */
        
        svn.checkForLocalModifications();
            
        pom.read();
        
        String version = pom.getProjectVersion();
        
        checkForSnapshotVersion(version);
        
        pom.checkForSnapshotDeps();
        
        
        String url = svn.getUrl(".");
        
        String projectName = pom.getArtifactId();
        
        closedVersion = closeVersion(version);
        
        Tag tag = new Tag();
        
        final String tagName = tag.getTagName(projectName, closedVersion);
        
        nextVersion = incVersion(closedVersion);
      
//        confirmator.setTagName(tagName); // TODO
        nextVersion = confirmator.confirm(tagName, nextVersion);
        
        nextVersion += "-SNAPSHOT";
        
            // Detect project layout
            Procedure proc = procedureFactory.get(url);
            
            proc.init(os, pom, params, undoStack, closedVersion, url, projectName, tagName);
        
            proc.exec();
        }
        catch(Exception e)
        {
//            except Exception as e:
//                print '''
//        -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
//                                     FAILURE!
//        -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
//        '''
//                if isinstance(e, MensagemUsuarioException): 
//                    print e
//                else:
//                    traceback.print_exc(e)
//                    
//                undoStack.undoAll()
//                
//                return 1
            
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n" +
                               "                             FAILURE!                                  \n" +
                               "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n"
            );

            if (e instanceof MensagemUsuarioException)
            {
                System.out.println(e.getMessage());
            }
            else
                e.printStackTrace();
            
            if (!undoStack.isEmpty())
            {
                System.out.println("\nPerforming rollback...");
                
                undoStack.undoAll();
            }
            
            return 1;
        }
        
//        # Change to next SNAPSHOT version
//        
//        pom.setProjectVersion(newVersion)
//        pom.save()
//        
//        # Commit
//        system('svn commit {pom} -m "[release] {oldVersion} => {newVersion}"'.format(pom        = pom.getFilename(), 
//                                                                                     oldVersion = version,
//                                                                                     newVersion = newVersion,
//                                                                                     ))
        
        // Change to next SNAPSHOT version
        pom.setProjectVersion(nextVersion);
        pom.save();
        
        ProcUtil.commitVersionChange(os, pom, closedVersion, nextVersion);
        
        return 0;
    }
    
    public static void main(String[] args)
    {
        Main main = new Main(new Svn(), new OS(), new SwingConfirmator());
        
        Pom pom = new Pom("pom.xml");
        
        int status = main.realMain(pom, args);
        
        System.exit(status); // senão a thread do Swing nunca morre e a aplicação nunca termina
    }

    void checkForSnapshotVersion(String version)
    {
        if (!Pom.isSnapshotVersion(version))
            throw new AlreadyReleasedException();
    }
}
