package moorea.maths.algebra;

import java.io.ObjectInputStream.GetField;

import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

/*

   A semiring is an algebraic structure, consisting of a nonempty
set R on which we have defined two operations, addition (usually
denoted by +) and multiplication (usually denoted by · or by con-
catenation) such that the following conditions hold:

   (1) Addition is associative and commutative and has a neutral ele-
       ment. That is to say, a+(b+c) = (a+b)+c and a+b = b+a
       for all a, b, c ∈ R and there exists a special element of R, usu-
       ally denoted by 0, such that a + 0 = 0 + a for all a ∈ R. It is
       very easy to prove that this element is unique.
 
   (2) Multiplication is associative and has a neutral element. That
       is to say, a(bc) = (ab)c for all a, b, c ∈ R and there exists a
       special element of R, usually denoted by 1, such that a1 =
       a = 1a for all a ∈ R. It is very easy to prove that this element
       too is unique. In order to avoid trivial cases, we will always
       assume that 1 = 0, thus insuring that R has at least two distinct
       elements.

   (3) Multiplication distributes over addition from either side. That
       is to say, a(b + c) = ab + ac and (a + b)c = ac + bc for all
       a, b, c ∈ R.     

   (4) The neutral element with respect to addition is multiplica-
       tively absorbing. That is to say, a0 = 0 = 0a for all a ∈ R.
       In other words, semirings are just “rings without subtraction”.
       
   citation from paper "SOME RECENT APPLICATIONS OF SEMIRING THEORY"
 */


public class Semiring<K> {

	Class<K> setClass;
	
	Operator<K> sumOperator; // combination operator
	Operator<K> dotOperator; // sumarisation operator
	
	K sumNeutralElement;
	K dotNeutralElement;
	
	Monoid<K> sumMonoid;
	Monoid<K> dotMonoid;

	// compare elements of the set over which is defined the semiring
	SetComparator<K> setComparator;
	
	String description = "Semiring";
	
	public static Integer maxValue=10000000;
	
	// semiring constructor:
	//		set
	//		sum operator
	//		sum neutral element
	//		dot operator
	//		dot neutral element
	//		name
	
	public static Semiring<Integer> shortestPathSR = new Semiring<Integer>(
			Integer.class,
			new SumOperator<Integer>(Integer.class), 0,
			new MinOperator<Integer>(Integer.class), maxValue,
			"shortest path SR");
	
	public static Semiring<Integer> shortestPathSRint = new Semiring<Integer>(
			Integer.class,
			new SumOperator<Integer>(Integer.class), 0,
			new MinOperator<Integer>(Integer.class), maxValue,
			"shortest path SR integer");
	
	public static Semiring<Double> shortestPathSRdouble = new Semiring<Double>(
			Double.class,
			new SumOperator<Double>(Double.class), 0.,
			new MinOperator<Double>(Double.class), (double) maxValue,
			"shortest path SR double");
	
	//public static final Semiring<Integer> shortestPathSR = new Semiring<Integer>(Integer.class,new SumOperator<Integer>(Integer.class), (Integer) 0, new MinOperator<Integer>(Integer.class), (Integer) 0 ,"shortest path SR");
	public static final Semiring<Integer> constraintOptimisationSR = new Semiring<Integer>(
			Integer.class,
			new SumOperator<Integer>(Integer.class), (Integer) 0,
			new MaxOperator<Integer>(Integer.class), (Integer) 0,
			"constraint optimisation SR");
	
	public static final Semiring<Double> probabilistMarginalisationSR = new Semiring<Double>(
			Double.class,
			new ProductOperator<Double>(Double.class), (Double) 1.,
			new SumOperator<Double>(Double.class), (Double) 0. ,
			"probabilist marginalisation SR");
	
	public static final Semiring<Double> probabilistMAPInferenceSR = new Semiring<Double>(
			Double.class,
			new ProductOperator<Double>(Double.class), (Double) 1.,
			new MaxOperator<Double>(Double.class), Double.MIN_VALUE,
			"probabilist MAP SR");	
	
	public Semiring(Class<K> sc, Operator<K> sumOp,  K sne, Operator<K> dotOp, K dne) {
		setClass = sc;
		setComparator = new SetComparator(sc);
		sumOperator = sumOp;
		//sumOperator.setInitialValue(sne);
		dotOperator = dotOp;
		//dotOperator.setInitialValue(dne);
		sumNeutralElement = sne;
		dotNeutralElement = dne;
		sumMonoid = new Monoid<K>(setClass,sumOperator,sumNeutralElement);
		dotMonoid = new Monoid<K>(setClass,dotOperator,dotNeutralElement);
	}

	public Semiring(Class<K> sc, Operator<K> sumOp,  K sne, Operator<K> dotOp, K dne, String description) {
		this(sc,sumOp,sne,dotOp,dne);
		this.description = description;
	}

	public SetComparator<K> getSetComparator() {
		return setComparator;
	}
	
	public Reducer<K> generateNewSumReducer() {
		return sumMonoid.generateNewReducer();
	}
	
	public Reducer<K> generateNewDotReducer() {
		return dotMonoid.generateNewReducer();
	}
	
	//
	
	public K getSumNeutralElement() {
		return sumNeutralElement;
	}

	public void setSumNeutralElement(K sumNeutralElement) {
		this.sumNeutralElement = sumNeutralElement;
	}

	public K getDotNeutralElement() {
		return dotNeutralElement;
	}

	public void setDotNeutralElement(K dotNeutralElement) {
		this.dotNeutralElement = dotNeutralElement;
	}

	public Operator<K> getSumOperator() {
		return sumOperator;
	}

	public void setSumOperator(Operator<K> sumOperator) {
		this.sumOperator = sumOperator;
	}

	public Operator<K> getDotOperator() {
		return dotOperator;
	}

	public void setDotOperator(Operator<K> dotOperator) {
		this.dotOperator = dotOperator;
	}

	public Class<K> getSetClass() {
		return setClass;
	}

	public void setSetClass(Class<K> setClass) {
		this.setClass = setClass;
	}

	public Monoid<K> getSumMonoid() {
		return new Monoid(sumMonoid);
	}

	public void setSumMonoid(Monoid<K> sumMonoid) {
		this.sumMonoid = sumMonoid;
	}

	public Monoid<K> getDotMonoid() {
		return dotMonoid;
	}
	
	public void setDotMonoid(Monoid<K> dotMonoid) {
		this.dotMonoid = dotMonoid;
	}

	public String toString() {
		return description+" set: "+setClass+" merge: "+sumMonoid+" marginalise: "+dotMonoid+"";
	}
	
	public static void main(String[] args) {
		// utility maximisation : combination: sum, marginalisation: max
		Semiring<Integer> constraintOptimisationSR = new Semiring<Integer>(Integer.class, new SumOperator<Integer>(Integer.class), (Integer) 0, new MaxOperator<Integer>(Integer.class), (Integer) 1 );
	
		// prob. marginalisation : combination: max, marginalisation: product
		Semiring<Double> probabilistMarginalisationSR = new Semiring<Double>(Double.class,new ProductOperator<Double>(Double.class), (Double) 1., new SumOperator<Double>(Double.class), (Double) 0. );
		
		// prob. MAP inference : combination: product, marginalisation: sum		
		Semiring<Double> probabilistMAPInferenceSR = new Semiring<Double>(Double.class, new ProductOperator<Double>(Double.class), (Double) 1. ,new MaxOperator<Double>(Double.class), Double.MIN_VALUE);

	}

}
