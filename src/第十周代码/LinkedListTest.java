package 第十周代码;

import java.util.LinkedList;

public class LinkedListTest {
	public static void main(String[] args) {
		LinkedList list = new LinkedList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.addFirst("bcd");
		list.addLast("nba");
		list.removeFirst();
		list.removeLast();
		list.getFirst();
		list.getLast();
		System.err.println(list);
	}
}
