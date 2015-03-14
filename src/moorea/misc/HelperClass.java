package moorea.misc;

import java.util.*;

/**
 * 
 * @author nicolas
 *
 */

public class HelperClass {
	
	
	public static int[] histogram(List<Integer> l) {
		if(l.size() == 0) {
			return new int[0];
		}
		int max = Integer.MIN_VALUE;
		for(Integer i : l) {
			if(i> max) {
				max = i;
			}
		}
		int[] histo = new int[max+1];
		for(Integer i : l) {
			histo[i]++;
		}
		return histo;
	}
	


	public static String doublePrintPaddingChar = " ";
	public static int doublePrintPrecisionRadix = 1;
	public static int doublePrintPrecisionDecimal = 4;
	
	public static String prettyFormatDouble(double d) {
		return prettyFormatDouble(d,doublePrintPrecisionRadix, doublePrintPrecisionDecimal);
	}
	
	public static String prettyFormatDouble(double d, int precisionRadix, int precisionDecimal) {
		//String doublePrintPaddingChar = " ";
		String s = String.format("%"//+doublePrintPaddingChar
									+
									((precisionRadix != 0)?
									""+(precisionRadix+precisionDecimal+1) // total number of char
									:
									""
									)
									+"."
									+""+precisionDecimal // decimal number of char
									+"f"
									, d);
		return s;
	}
	
	public static void main(String[] args) {
		double d = 8.4572243593;
		System.out.println(prettyFormatDouble(d, 4, 3));
	}
}
