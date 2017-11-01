package com.github.tfga.release.confirm;

public class ConfirmatorMock implements Confirmator
{
    @Override
    public String confirm(String tagName, String nextVersion)
    {
        return nextVersion;
    }
}
