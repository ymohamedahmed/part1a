package supo3;

public class OOPLinkedListElement<T> implements Cloneable {
	private T x;
	private OOPLinkedListElement<T> next;

	public OOPLinkedListElement(T x, OOPLinkedListElement<T> next) {
		this.x = x;
		this.next = next;
	}

	public void setVal(T x) {
		this.x = x;
	}

	public T getVal() {
		return x;
	}

	public OOPLinkedListElement<T> getNext() {
		return next;
	}

	public void setNext(OOPLinkedListElement<T> next) {
		this.next = next;
	}

	public OOPLinkedListElement<T> clone() throws CloneNotSupportedException {
		OOPLinkedListElement<T> clone = (OOPLinkedListElement<T>) super.clone();
		clone.setNext((OOPLinkedListElement<T>) next.clone());
		return clone;
	}
}
