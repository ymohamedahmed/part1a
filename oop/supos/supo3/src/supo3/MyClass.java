package supo3;

public class MyClass implements Cloneable{
	private String mName;
	private int[] mData;

	public MyClass(String name, int[] data) {
		mName = name;
		mData = data;
	}
	// Copy constructor
	public MyClass(MyClass toCopy) {
		this.mName = toCopy.mName;
		this.mData = toCopy.mData.clone();
	}
	public static void main(String[] args) {
		int[] test = {1,2,3};
		MyClass orig = new MyClass("Bob", test);
		MyClass copy = new MyClass(orig);
		System.out.println("test");
	}
	public Object clone() throws CloneNotSupportedException {
		MyClass copy = (MyClass) super.clone();
		copy.mData = mData.clone();
		return copy;
	}
}
