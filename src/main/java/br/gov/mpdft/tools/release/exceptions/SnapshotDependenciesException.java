package br.gov.mpdft.tools.release.exceptions;

import java.util.List;

import br.gov.mpdft.tools.release.Pom;
import br.gov.mpdft.util.string.Formatter;
import br.gov.mpdft.util.string.StringUtils;

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
               StringUtils.toStringEntreVirgulas(snapshotDeps, "\n", new Formatter<Element>()
               {
                   @Override
                   public String format(Element dep)
                   {
                       return Pom.formatDependency(dep);
                   }
               });
    }
}
