package com.github.tfga.release;

import java.util.List;

import com.github.tfga.release.os.OS;

public interface Procedure
{
    void exec();

    void init(OS os, Pom pom, List<String> params, UndoStack undoStack, String closedVersion, String url, 
              String projectName, String tagName);
}
