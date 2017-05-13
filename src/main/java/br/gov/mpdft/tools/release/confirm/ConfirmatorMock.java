package br.gov.mpdft.tools.release.confirm;

public class ConfirmatorMock implements Confirmator
{
    @Override
    public String confirm(String tagName, String nextVersion)
    {
        return nextVersion;
    }
}
