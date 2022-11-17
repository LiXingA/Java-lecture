package 第十周上机;

import java.util.LinkedHashSet;

public class LinkedHashSetTest {
	public static void main(String[] args) {
		LinkedHashSet<String> linkedSet = new LinkedHashSet<>();
		linkedSet.add(" Java");
		linkedSet.add(" C");
		linkedSet.add(" Python");
		linkedSet.add(" C++");
		linkedSet.add(" Visual Basic");
		linkedSet.add(" .NET");
		linkedSet.add(" JavaScript");
		for (String string : linkedSet) {
			System.out.println(string);
		}
	}
}
