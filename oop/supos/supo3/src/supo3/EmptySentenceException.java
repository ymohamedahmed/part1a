package supo3;

public class EmptySentenceException extends Exception {
	public EmptySentenceException() {
		super("Supplied string empty");
	}
}
