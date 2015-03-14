package moorea.misc;

/**
 * 
 * @author nicolas
 *
 */

public class ArrayMisc {
	
	public static <K> void dispArray(K[] t) {
		System.out.print("[ ");
		for(int i=0;i<t.length; i++) {
			System.out.print(t[i]+" ");
		}
		System.out.println("]");
	}
	
	public static void dispArray(int[] t) {
		System.out.print("[ ");
		for(int i=0;i<t.length; i++) {
			System.out.print(t[i]+" ");
		}
		System.out.println("]");
	}

	public static void dispArrayWithIndexes(int[] t) {
		String sIndexes = "";
		String sValues = "";
		for(int i=0;i<t.length; i++) {
			int padSpace = 1 + (int) (Math.log(t[i])/Math.log(10));
			if(padSpace > 1) {
				sIndexes += String.format("% "+padSpace+"d ",i);
			} else {
				sIndexes += ""+i+" ";
			}
			sValues += t[i]+" ";
		}
		System.out.println("  "+sIndexes);
		System.out.println("[ "+sValues+"]");
	}
	
	public static int dispArrayIndexes(int[] t) {
		int max = Integer.MIN_VALUE;
		for(int i=0;i<t.length; i++) {
			if(t[i] > max) {
				max = i;
			}
		}
		System.out.println("   ");
		int padSpace = 1 + (int) Math.log(max);
		for(int i=0;i<t.length; i++) {
			System.out.printf("% "+padSpace+"d ",i);
		}
		System.out.println();
		return padSpace;
	}
	
	public static void dispArray(double[] t) {
		System.out.println("[ ");
		for(int i=0;i<t.length; i++) {
			System.out.print(t[i]+" ");
		}
		System.out.println("]");
	}

}
