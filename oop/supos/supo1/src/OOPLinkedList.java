import java.util.LinkedList;

public class OOPLinkedList {
	private LinkedList<OOPLinkedListElement> list;

	public OOPLinkedList() {
		list = new LinkedList<>();
	}
	public void addHead(OOPLinkedListElement x) {
		list.addFirst(x);
	}
	public void remHead() {
		list.removeFirst();
	}
	public OOPLinkedListElement nth(int n) {
		return list.get(n);
	}
	public int length() {
		return list.size();
	}
}
