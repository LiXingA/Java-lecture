package 第十四周代码;

import java.util.HashSet;

public class HashSetTest {
	public static void main(String[] args) {
		HashSet<Object> hashSet = new HashSet<>();
		hashSet.add("Java语言");
		hashSet.add("Visual Basic语言");
		hashSet.add("Python语言");
		hashSet.add("C语言");
		hashSet.add("Python语言");
		hashSet.add("Java语言");
		for (Object object : hashSet) {
			System.out.println(object);
		}
	}

}
