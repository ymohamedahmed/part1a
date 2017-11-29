public class OOPLinkedList implements Cloneable {
	private OOPLinkedListElement head;

	public OOPLinkedList(OOPLinkedListElement head) {
		this.head = head;
	}

	public void addHead(OOPLinkedListElement x) {
		x.setNext(head);
		head = x;
	}

	public void remHead() {
		head = head.getNext();
	}

	public OOPLinkedListElement nth(int n) {
		OOPLinkedListElement nth = head;
		for (int i = 0; i <= n; i++) {
			nth = nth.getNext();
		}
		return nth;
	}

	public int length() {
		int n = 0;
		OOPLinkedListElement current = head;
		while (current.getNext() != null) {
			current = current.getNext();
			n++;
		}
		return n;
	}

	public Object clone() throws CloneNotSupportedException {
		// Shallow clone
		OOPLinkedList clone = (OOPLinkedList) super.clone();
		
		return clone;
	}
}
