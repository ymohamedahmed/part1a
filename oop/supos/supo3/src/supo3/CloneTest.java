package supo3;

public class CloneTest implements Cloneable {
	private final int[] mData = new int[100];

	public Object clone() throws CloneNotSupportedException {
		CloneTest copy = (CloneTest) super.clone();
		// Doesn't compile since mData cannot be reassigned since it's final
		//copy.mData = mData.clone();
		return copy;
	}
}
