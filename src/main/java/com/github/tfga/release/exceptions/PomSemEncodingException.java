package com.github.tfga.release.exceptions;

public class PomSemEncodingException extends MensagemUsuarioException
{
    public PomSemEncodingException()
    {
        super("'pom.xml' sem encoding.\n" +
              "\n" +
              "Acrescente\n" +
              "\n" +
              "    <?xml version=\"1.0\" encoding=\"[UTF-8|ISO-8859-1]\"?>\n" +
              "\n" +
              "no começo do arquivo.");
    }
    
//    @Override
//    public String toString()
//    {
//        return "shit";
//    }
//    
//    @Override
//    public String getMessage()
//    {
//        // TODO Auto-generated method stub
//        return super.getMessage();
//    }
}
