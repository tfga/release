package br.gov.mpdft.tools.release.os;

public class OsMock extends OS
{
    public void system(String cmd)
    {
        System.out.printf("[mock] >>> %s\n", cmd);
    }
}
