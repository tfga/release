package com.github.tfga.release.confirm;

public interface Confirmator
{
    String confirm(String tagName, String nextVersion);
}
