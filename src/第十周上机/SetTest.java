package 第十周上机;

import java.util.HashSet;

public class SetTest {

	public static void main(String[] args) {
		HashSet set = new HashSet<>();
		set.add("aaa");
		set.add("bbb");
		set.add("ccc");
		set.add("ddd");
		set.add("ddd");
		set.add("ddd");
		set.add("ddd");
		set.add("ddd");
		set.add("ddd");
		for (Object object : set) {
			System.out.println(object);
		}
	}
}
