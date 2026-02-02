
package net.sf.finanx.utils;

public class OSDetector {
 
	public static final int WINDOWS = 1;
	public static final int MAC = 2;
	public static final int UNIX = 3;
	public static final int SOLARIS = 4;
	
	public static boolean isWindows() {
 		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
 	}
 
	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
 
	public static boolean isUnix() {
 		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
 	}
 
	public static boolean isSolaris() {
 		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("sunos") >= 0);
 	}
	
	public int getOS(){
		if (isUnix()) return UNIX;
		else if (isWindows()) return WINDOWS;
		else if (isMac()) return MAC;
		else if (isSolaris()) return SOLARIS;
		else return 0;
	}
 
}