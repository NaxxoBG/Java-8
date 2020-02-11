package java8;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SandboxTests {
	
	@Test
	public void loopFinder() {
		Node node3 = new Node(null);
		Node node2 = new Node(node3);
		Node node1 = new Node(node2);
		Node node7 = new Node(node3);
		Node node6 = new Node(node7);
		Node node5 = new Node(node6);
		Node node4 = new Node(node5);
		node3.setNext(node4);

		System.out.println("Loop size: " + Sandbox.getLoopLength(node1));
		System.out.println("Loop size: " + Sandbox.floydCycleAlgo(node1));
		assertEquals(5, Sandbox.getLoopLength(node1));
		assertEquals(5, Sandbox.floydCycleAlgo(node1));
	}
}
