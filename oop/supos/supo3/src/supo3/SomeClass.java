package supo3;

public class SomeClass extends SomeOtherClass implements Cloneable {
	private int[] mData;

	public Object clone() {
		SomeClass sc = new SomeClass();
		sc.mData = mData.clone();
		return sc;
	}

	public static void main(String[] args) {
		SomeClass sc = new SomeClass();
		sc.setVal(10);
		sc.mData = new int[2];
		sc.mData[0] = 1;
		sc.mData[1] = 2;
		SomeClass clone = (SomeClass) sc.clone();
		// mData is copied to a new reference
		// But get val outputs 0
		System.out.println(clone.getVal());
	}

}
