package supo3;

public class OOPLinkedList<T> implements Cloneable, OOPList<T> {
	private OOPLinkedListElement<T> head;

	public OOPLinkedList(OOPLinkedListElement<T> head) {
		this.head = head;
	}

	public void addHead(OOPLinkedListElement<T> x) {
		x.setNext(head);
		head = x;
	}

	public void remHead() {
		head = head.getNext();
	}
	public void append(OOPLinkedListElement<T> x) {
		OOPLinkedListElement<T> current = head;
		while(current.getNext() != null) {
			current = current.getNext();
		}
		current.setNext(x);
	}

	public T nth(int n) {
		OOPLinkedListElement<T> nth = head;
		for (int i = 0; i <= n; i++) {
			nth = nth.getNext();
		}
		return nth.getVal();
	}

	public int length() {
		int n = 0;
		OOPLinkedListElement<T> current = head;
		while (current.getNext() != null) {
			current = current.getNext();
			n++;
		}
		return n;
	}

	public OOPLinkedListElement<T> getHead() {
		return head;
	}

	public OOPLinkedList<T> clone() throws CloneNotSupportedException {
		// Shallow clone
		OOPLinkedList<T> clone = (OOPLinkedList<T>) super.clone();
		// Deep clone
		OOPLinkedListElement<T> current = clone.getHead();
		while(current.getNext()!=null) {
			current = (OOPLinkedListElement<T>)current.clone();
			current = current.getNext();
		}
		return clone;
	}

	@Override
	public void update(int n, T o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void append(T o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int n) {
		// TODO Auto-generated method stub
		
	}
}
