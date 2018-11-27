package com.github.tfga.release.exceptions;

import static com.github.tfga.release.util.StringUtils.join;
import static com.github.underscore.U.chain;

import java.util.List;

import com.github.tfga.release.Pom;
import com.github.tfga.release.util.StringUtils;

import de.pdark.decentxml.Element;

public class SnapshotDependenciesException extends MensagemUsuarioException
{
    private List<Element> snapshotDeps;

    public SnapshotDependenciesException(List<Element> snapshotDeps)
    {
        this.snapshotDeps = snapshotDeps;
    }
    
    @Override
    public String getMessage()
    {
        return "Snapshot dependencies found:\n\n" +
               join(snapshotDeps, "\n", Pom::formatDependency);
    }
}
