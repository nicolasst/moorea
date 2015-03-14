package moorea.misc;

import java.util.LinkedList;
import java.util.List;

/**
 * Aggregates the duration of several benchmarks.
 * 
 * @author nicolas
 *
 */

public class ComplexBenchmark {
	
	List<Benchmark> benchmarkList = new LinkedList<>();
	
	String description = "";
	
	public ComplexBenchmark() {
	}
	
	public ComplexBenchmark(String description) {
		this.description = description;
	}
	
	public void add(Benchmark b) {
		benchmarkList.add(b);
	}
	
	public long sumDurations() {
		long total = 0L;
		for(Benchmark b : benchmarkList) {
			total += b.getDuration();
		}
		return total;
	}
	
	public void display() {
		System.out.println(toString());
	}
	
	public void display(boolean verbose) {
		if(verbose) {
			System.out.println("=== Complex benchmark: "+description);
			for(Benchmark b : benchmarkList) {
				System.out.println("= "+b);
			}
			System.out.println("=== total : "+Benchmark.timeDifferenceToString(sumDurations()));
		} else {
			display();
		}
	}
	
	public void display(String description) {
		this.description = description;
		System.out.println(toString());
	}
	
	public String toString() {
		return "complex benchmark: total duration : "+description+" : "+Benchmark.timeDifferenceToString(sumDurations());
	}

}
