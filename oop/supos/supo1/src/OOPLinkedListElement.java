
public class OOPLinkedListElement implements Cloneable {
	private int x;
	private OOPLinkedListElement next;

	public OOPLinkedListElement(int x, OOPLinkedListElement next) {
		this.x = x;
		this.next = next;
	}

	public void setVal(int x) {
		this.x = x;
	}

	public int getVal() {
		return x;
	}

	public OOPLinkedListElement getNext() {
		return next;
	}

	public void setNext(OOPLinkedListElement next) {
		this.next = next;
	}

	public Object clone() throws CloneNotSupportedException {
		OOPLinkedListElement clone = (OOPLinkedListElement) super.clone();
		return clone;
	}
}
