package 第十周上机;

import java.util.TreeSet;

public class TreeSetTest {
public static void main(String[] args) {
	TreeSet<String> treeSet = new TreeSet<>();
	treeSet.add("b");
	treeSet.add("g");
	treeSet.add("a");
	treeSet.add("i");
	treeSet.add("p");
	treeSet.add("a");
	treeSet.add("c");
	treeSet.add("a");
	for (String string : treeSet) {
		System.out.println(string);
		
	}
}
}
