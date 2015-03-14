package moorea.misc;

import java.util.Date;


import org.apache.commons.lang3.time.DurationFormatUtils;



/*

Unrelated to this class: How to benchmark in command line, and get object usage

java -agentlib:hprof=help

     HPROF: Heap and CPU Profiling Agent (JVM TI Demonstration Code)

	hprof usage: java -agentlib:hprof=[help]|[<option>=<value>, ...]

	Option Name and Value  Description                    Default
	---------------------  -----------                    -------
	heap=dump|sites|all    heap profiling                 all
	cpu=samples|times|old  CPU usage                      off
	
	  - Use of the -Xrunhprof interface can still be used, e.g.
       java -Xrunhprof:[help]|[<option>=<value>, ...]
    will behave exactly the same as:
       java -agentlib:hprof=[help]|[<option>=<value>, ...]
       
   -agentlib:hprof=heap=sites,cpu=times
*/

/* to run in terminal, export the project subfolder to a non executable jar
 
 java -cp testfsp.jar:../workspace/libraries/commons-lang3-3.3.2/commons-lang3-3.3.2.jar:../workspace/libraries/commons-collections4-4.0/commons-collections4-4.0.jar  moorea.graphs.algorithms.fastsp.TestFSP

 */

/**
 * 
 * Quick and easy way to measure function execution time.
 * 
 * Usage:
 * 
 * (short)
 * Benchmark bm = new Benchmark(true,"description");
 * function();
 * bm.stop(true);
 * =
 * (long)
 * Benchmark bm = new Benchmark("description");
 * bm.start()
 * function();
 * bm.stop();
 * bm.display();
 * 
 * Display results:
 * - bm.stop(true);
 * - bm.display();
 * - System.out.println(bm);
 * 
 */

public class Benchmark {
	
	String description = "";
	
	public Date dateStart, dateEnd;
	
	public Benchmark() {
		
	}
	
	public Benchmark(String descr) {
		description = descr;
	}
	
	public Benchmark(boolean start) {
		if(start) {
			start();
		}
	}
	
	public Benchmark(boolean start, String descr) {
		if(start) {
			start();
		}
		description = descr;
	}
	
	public Benchmark(Benchmark b) {
		dateStart = b.dateStart;
		dateEnd = b.dateEnd;
	}
	
	//
	
	public void start() {
		dateStart = new Date();
	}
	
	public void stop() {
		dateEnd = new Date();
	}
	
	public void stop(boolean display) {
		stop();
		display();
	}

	public void display() {
		System.out.println("benchmark: "+description);
		printRunningTime();
	}

	public void printRunningTime() {
		printRunningTime(dateStart, dateEnd);	
	}

	public long getDuration() {
		return timeDifference(dateStart, dateEnd);
	}
	
	public String toString() {
		return "benchmark: "+description+" : "+timeDifferenceToString(dateStart, dateEnd);
	}
	
	// static methods
	
	public static long timeDifference(Date dateStart, Date dateEnd) {
		return (dateEnd.getTime()-dateStart.getTime());
	}	
		
	public static void printRunningTime(Date dateStart, Date dateEnd) {
		printRunningTime(timeDifferenceToString(dateStart, dateEnd));
	}
	
	public static void printRunningTime(String duration) {
		System.out.println("ellapsed time: "+duration);
	}
	
	public static String timeDifferenceToString(Date dateStart, Date dateEnd) {
		long timeDiff = timeDifference(dateStart, dateEnd);
		return timeDifferenceToString(timeDiff);
	}
	
	public static String timeDifferenceToString(long timeDiff) {
		String formattedDate = DurationFormatUtils.formatDuration(timeDiff, "d 'days' H 'hours' m 'mins' s 'secs'");
		return formattedDate+" ("+timeDiff+" ms)";
	}

}
