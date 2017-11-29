package supo3;

public class RetValTest {
	public static String sEmail = "";

	public static void extractCamEmail(String sentence) throws EmptySentenceException, NoCamEmailFoundException {
		if (sentence == null || sentence.length() == 0)
			throw new EmptySentenceException(); // Error - sentence empty
		String tokens[] = sentence.split(" "); // split into tokens
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].endsWith("@cam.ac.uk")) {
				sEmail = tokens[i];
			}
		}
		throw new NoCamEmailFoundException(); // Error - no cam email found
	}

	public static void main(String[] args) {
		try {
			RetValTest.extractCamEmail("My email is rkh23@cam.ac.uk");
			System.out.println("Success: " + RetValTest.sEmail);
		} catch (EmptySentenceException e) {
			e.printStackTrace();
		} catch (NoCamEmailFoundException e) {
			e.printStackTrace();
		}
	}
}