package moorea.misc;

/**
 * A Logger can write on any stream.
 * 
 * @author nicolas
 *
 */

public abstract class Logger {

	public abstract void print(String str);

	public abstract void flush();
	
	public abstract void close();
	
	//
	
	public void println(String str) {
		print(str+"\n");
	}

	public void print(Object o) {
		print(o.toString());
	}

	public void println(Object o) {
		println(o.toString());
	}
	
	public void print(int i) {
		print(""+i);
	}

	public void println(int i) {
		println(""+i);
	}
	
	public void print(long l) {
		print(""+l);
	}

	public void println(long l) {
		println(""+l);
	}
	
	public void print(double d) {
		print(""+d);
	}

	public void println(double d) {
		print(""+d);
	}
	
	public void print(boolean b) {
		print(""+b);
	}

	public void println(boolean b) {
		print(""+b);
	}
	
	public void println() {
		print("\n");
	}
	
}
