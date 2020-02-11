package java8;

public class Node {
	private Node next;

	public Node(Node next) {
		this.next = next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getNext() {
		return next;
	}

	@Override
	public String toString() {
		return "Node [Next node=" + next + "]";
	}
}
