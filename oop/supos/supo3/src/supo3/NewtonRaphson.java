package supo3;

public class NewtonRaphson {
	public static double sqrt(double a) throws NegativeSquareRootException {
		// Function for computing sqrt
		// x_n+1 = x_n - (x_n^2 - a)/2x_n
		if(a < 0) {
			throw new NegativeSquareRootException();
		}
		double error = 0.0000000001;
		double x = 1;
		double x_next = x - (Math.pow(x, 2) - a) / (2 * x);
		do {
			x = x_next;
			x_next =  x - (Math.pow(x, 2) - a) / (2 * x);
		} while (Math.abs(x_next - x) > error);
		//Check that answer is roughly equal to answer from Math library
		assert(Math.abs(x_next - Math.sqrt(a)) < error);
		return x_next;
	}

	public static void main(String[] args) {
		try {
			System.out.println(sqrt(0));
		} catch (NegativeSquareRootException e) {
			e.printStackTrace();
		}
	}
}
