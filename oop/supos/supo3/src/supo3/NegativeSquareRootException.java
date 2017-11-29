package supo3;

public class NegativeSquareRootException extends Exception{
	public NegativeSquareRootException() {
		super("Square root of negative number cannot be computed");
	}
}
