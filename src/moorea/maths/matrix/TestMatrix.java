package moorea.maths.matrix;

import java.util.Iterator;
import java.util.Random;

import moorea.maths.random.UniformRandomIntegerGenerator;


/**
 * 
 * @author nicolas
 *
 */

public class TestMatrix {


	public static void main(String[] args) {

		
		Random r = new Random();
		
		Iterator<Integer> it = new UniformRandomIntegerGenerator(r, 0, 10);
		
		Matrix<Integer> m = MatrixFactory.generateRandomMatrix(Integer.class, 5, it);
		
		MatrixAlgorithms.display(m);
		
		m = MatrixFactory.generateRandomSymetricMatrix(Integer.class, 5, it);
		
		MatrixAlgorithms.display(m);
		
	}

}
