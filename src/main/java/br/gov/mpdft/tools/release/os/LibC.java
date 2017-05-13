package br.gov.mpdft.tools.release.os;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
 
/** Simple example of native library declaration and usage. */
public class LibC
{
    public interface CLibrary extends Library
    {
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);
        int system(String cmd);
    }
 
//    public static void main(String[] args)
//    {
//        CLibrary.INSTANCE.printf("Hello, World\n");
//        
//        for (int i = 0; i < args.length; i++)
//        {
//            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
//        }
//        
//        system("less src/HelloWorld.java");
//    }

	public static int system(String cmd)
	{
		return CLibrary.INSTANCE.system(cmd);
	}
}
