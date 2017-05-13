package br.gov.mpdft.tools.release.confirm;

public interface Confirmator
{
    String confirm(String tagName, String nextVersion);
}
