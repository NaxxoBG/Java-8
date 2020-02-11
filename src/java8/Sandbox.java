package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/** Just some kata challenges.
 * @author Naxxo
 */
public class Sandbox {

	public static int getLoopLength(Node node) {
		HashMap<Integer, Integer> hashes = new HashMap<Integer, Integer>();
		int counter = 0;
		while (!Objects.isNull(node) && !hashes.containsKey(node.hashCode())) {
			hashes.put(node.hashCode(), counter++);
			node = node.getNext();
		}
		return Objects.isNull(node) ? 0 : hashes.size() - hashes.get(node.hashCode());
	}

	public static int floydCycleAlgo(Node node) {
		int len = 0;
		Node a = node;
		Node b = node.getNext();

		while(a.hashCode() != b.hashCode()) {
			a = a.getNext();
			b = b.getNext().getNext();
		}

		do {
			len++;
			b = b.getNext();
		} while (a.hashCode() != b.hashCode());

		return len;
	}

	public static String seriesSum(int n) {
		return String.format("%.2f",
				DoubleStream.iterate(1, e -> e + 1)
				.limit(n - 1)
				.map(e -> 1 / (3 * e + 1))
				.sum() + 1);
	}

	/**
	 * An isogram is a word that has no repeating letters, consecutive or non-consecutive. 
	 * Implement a function that determines whether a string that contains only letters 
	 * is an isogram. Assume the empty string is an isogram. Ignore letter case.
	 * @param str
	 * @return
	 */
	public static boolean isIsogram(String str) { 
		return str.toLowerCase().chars().distinct().count() == str.length();
	}

	/** A function that accepts an array of 10 integers (between 0 and 9), that returns a string of those numbers 
	 * in the form of a phone number "(123) 456-7890". 
	 * @param numbers
	 * @return
	 */
	public static String createPhoneNumber(int[] numbers) {
		String str = Arrays.toString(numbers).replaceAll("[\\[,\\] ]", "");
		return String.format("(%s) %s-%s", str.substring(0, 3), str.substring(3, 6), str.substring(6, 10));
	}

	public static boolean validatePin(String pin) {
		return pin.matches("[\\d]{4}|[\\d]{6}");
	}

	public static int duplicateCount(String text) {
		return (int) new ArrayList<Long>(text.chars()
				.mapToObj(i -> (char) i)
				.map(Character::toLowerCase)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.values())
				.stream()
				.filter(e -> e > 1)
				.count();
	}

	public static int sortDesc(final int num) {
		return Integer.parseInt(
				Arrays.asList(Integer.toString(num).split(""))
				.stream()
				.sorted((a, b) -> b.compareTo(a))
				.reduce("", String::concat));
	}

	public static int thirt(int number) {
		int[] pattern = {1, 10, 9, 12, 3, 4};
		String[] numLength = new StringBuilder().append(new Integer(number).toString()).reverse().toString().split("");
		int sum = 0, oldSum = 0;
		System.out.println(Arrays.toString(numLength));
		while (true) {
			sum = 0;
			for (int i = 0; i < (numLength.length > pattern.length ? numLength.length : pattern.length); i++) {			
				sum += Integer.parseInt(numLength[i]) * pattern[i];
//				System.out.println("asd " + i);
//				System.out.println(numLength[i]);
			}
			if (sum == oldSum) {
				break;
			}
			numLength = new StringBuilder().append(new Integer(sum).toString()).reverse().toString().split("");
			oldSum = sum;
		}
		return sum;
	}

	public static void main(String[] args) {
		System.out.println(seriesSum(2));
		System.out.println("1. " + isIsogram("aba"));
		System.out.println("2. " + isIsogram("Dermatoglyphics"));
		System.out.println("3. " + isIsogram("moose"));
		System.out.println("4. " + isIsogram("isogram"));
		System.out.println("5. " + isIsogram("isIsogram"));
		System.out.println("6. " + isIsogram("moOse"));
		System.out.println("7. " + isIsogram("thumbscrewjapingly"));
		System.out.println(createPhoneNumber(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 }));

		System.out.println(validatePin("1"));
		System.out.println(validatePin("12"));
		System.out.println(validatePin("123"));
		System.out.println(validatePin("12345"));
		System.out.println(validatePin("1234567"));
		System.out.println(validatePin("1234"));
		System.out.println(validatePin("1.234"));
		System.out.println(validatePin("00000000"));
		System.out.println(validatePin("123456"));

		System.out.println(duplicateCount("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZReturnsTwentySix"));

		System.out.println(sortDesc(132631));

		Node node3 = new Node(null);
		Node node2 = new Node(node3);
		Node node1 = new Node(node2);
		Node node7 = new Node(node3);
		Node node6 = new Node(node7);
		Node node5 = new Node(node6);
		Node node4 = new Node(node5);
		node3.setNext(node4);

		System.out.println("Loop size: " + getLoopLength(node1));
		System.out.println("Loop size: " + floydCycleAlgo(node1));

		System.out.println(thirt(1234567));
	}
}
