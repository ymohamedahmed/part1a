package supo3;

public class NoCamEmailFoundException extends Exception {
	public NoCamEmailFoundException() {
		super("No @cam address in supplied string");
	}
}
