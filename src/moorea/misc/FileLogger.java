package moorea.misc;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Use to do easy wile writting as easy as system.out.println.
 * 
 * @author nicolas
 *
 */

public class FileLogger extends Logger {

    FileWriter fw;
    PrintWriter pw;
    
    boolean flush;
	
	public FileLogger(String fname) {
	    try {
	    	fw = new FileWriter(fname);
	    	pw = new PrintWriter(fw);
	    } catch(Exception ex) {
	    	System.out.println(ex);
	    	ex.printStackTrace();
	    	System.exit(1);
	    }      
	}
	
	public FileLogger(String fname, boolean flush) {
	    try {
	    	fw = new FileWriter(fname);
	    	pw = new PrintWriter(fw);
	    } catch(Exception ex) {
	    	System.out.println(ex);
	    	ex.printStackTrace();
	    	System.exit(1);
	    }      
	    this.flush = flush;
	}
	
	public void print(String str) {
		try {
			pw.print(str);
			if(flush) {
				pw.flush();
			}
		} catch(Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(1);
		}
	}
    
    public void close() {
        try {
            pw.flush();
            pw.close();
        } catch(Exception ex) {
        	System.out.println(ex);
        	ex.printStackTrace();
        	System.exit(1);
        }
    }
    
    public static void writeListToFile(String fname, List l) {
    	writeListToFile(fname, l , " ");
    }
    
    public static void writeListToFile(String fname, List l, String sep) {
    	FileLogger fl = new FileLogger(fname);
    	for(Object o : l) {
    		fl.print(o+sep);
    	}
    	fl.close();
    }
    
	public void flush() {
		pw.flush();
	}
	
}
