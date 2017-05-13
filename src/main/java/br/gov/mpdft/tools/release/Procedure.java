package br.gov.mpdft.tools.release;

import java.util.List;

import br.gov.mpdft.tools.release.os.OS;

public interface Procedure
{
    void exec();

    void init(OS os, Pom pom, List<String> params, UndoStack undoStack, String closedVersion, String url, 
              String projectName, String tagName);
}
