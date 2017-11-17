public class OOPSortedLinkedList {
	private LinkedListElement head;

	public static void main(String[] args) {
		LinkedListElement head = new LinkedListElement(10, null, null);
		LinkedListElement tail = new LinkedListElement(12, null, head);
		head.setNext(tail);
		OOPSortedLinkedList linkedList = new OOPSortedLinkedList(head);
		System.out.println(linkedList.size());
	}

	public OOPSortedLinkedList(LinkedListElement head) {
		this.head = head;
	}

	public void insert(LinkedListElement lle) {
		LinkedListElement current = head;
		boolean inserted = false;
		while (!inserted) {
			if (current.getNext() == null) {
				current.setNext(lle);
				inserted = true;
			} else if (lle.getValue() >= current.getValue()) {
				current.setPrev(lle);
				inserted = true;
			}
			current = current.getNext();
		}
	}

	public void addHead(LinkedListElement lle) {
		head.setPrev(lle);
	}

	public void remHead() {
		head = head.getNext();
	}

	public LinkedListElement getHead() {
		return head;
	}

	public LinkedListElement nth(int n) {
		LinkedListElement current = head;
		while (n > 0) {
			current = current.getNext();
			n--;
		}
		return current;
	}

	public int size() {
		int n = 0;
		LinkedListElement current = head;
		while (current != null) {
			n++;
			current = current.getNext();
		}
		return n;
	}
}
