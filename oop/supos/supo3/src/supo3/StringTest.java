package supo3;

public class StringTest {
	final int[] m = new int[2];
	public static void main(String[] args) {
		/*String s1 = new String("Hi");
		String s2 = new String("Hi");
		System.out.println( (s1==s2) );
		String s3 = "Hi";
		String s4 = "Hi";
		System.out.println( (s3==s4) );
		System.out.println(s1==s3);
		System.out.println((x()));*/
		StringTest s = new StringTest();
		s.m[0] = 1;
		s.m[1] = 9;
		int[] mCopy = s.m.clone();
		System.out.println("end");
	}
	public static int x() {
		try {
			return 6;
		}finally {
			System.out.println("finally");
		}
	}
}
