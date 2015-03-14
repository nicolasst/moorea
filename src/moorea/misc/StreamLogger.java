package moorea.misc;

import java.io.PrintStream;

/**
 * 
 * @author nicolas
 *
 */

public class StreamLogger extends Logger {

		PrintStream ps;
		
		boolean flush;
		
		public StreamLogger(PrintStream ps) {
			this.ps = ps;
		}
		
		public StreamLogger(PrintStream ps, boolean flush) {
			this.ps = ps;
			this.flush = flush;
		}

		
		public void print(String str) {
			ps.print(str);
			if(flush) {
				ps.flush();
			}
		}
	    
	    public void close() {
	    	ps.close();
	    }

		public void flush() {
			ps.flush();
		}
		
	}
