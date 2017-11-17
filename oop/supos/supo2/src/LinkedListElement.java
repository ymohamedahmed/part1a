public class LinkedListElement {
	private int value;
	private LinkedListElement next;
	private LinkedListElement prev;
	public LinkedListElement(int value, LinkedListElement next, LinkedListElement prev) {
		this.value = value;
		this.next = next;
		this.prev = prev;
	}
	public int getValue() {
		return value;
	}
	public LinkedListElement getNext() {
		return next;
	}
	public LinkedListElement getPrev() {
		return prev;
	}
	public void setNext(LinkedListElement next) {
		this.next = next;
	}
	public void setPrev(LinkedListElement prev) {
		this.prev = prev;
	}
}
