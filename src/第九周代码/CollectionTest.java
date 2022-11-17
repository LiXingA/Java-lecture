package 第九周代码;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CollectionTest {

	public static void main(String[] args) {
		ArrayList list = new ArrayList<>();
		list.add("abc");
		if (list.contains("abc")) {
			System.out.println("包含abc");
		} else {
			System.out.println("不包含abc");
		}

	}

	public static void main5(String[] args) {
		String a = "A";
		String b = "B";
		String c = "C";
		Collection<String> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		Collection<String> list2 = new ArrayList<>();
		list2.add(b);
		list2.add(c);
		boolean retainAll = list.retainAll(list2);
		System.err.println("是否移出成功：" + retainAll);
		System.out.println(list);
	}

	public static void main4(String[] args) {
		String a = "A";
		String b = "B";
		String c = "C";
		Collection<String> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		Collection<String> list2 = new ArrayList<>();
		list2.add(b);
		list2.add(c);
		boolean removeAll = list.removeAll(list2);
		System.err.println("是否移出成功：" + removeAll);
		System.out.println(list);
	}

	public static void main2(String[] args) {
		List list = new ArrayList<>();
		Integer integer = new Integer(123);
		list.add(integer);
		Integer.valueOf(123);
		list.add(123);
		Boolean.valueOf(true);
		list.add(true);
		list.add("abc");
		list.add(new Dog("小狗丹妮"));
		System.err.println(list);

		list.remove(integer);
		list.remove(integer);
		list.remove(true);
		list.remove("abc");
		System.err.println(list);

	}

	public static void main3(String[] args) {
		Collection<String> ct = new ArrayList<String>();
		ct.add("abcd");
		ct.add("abcd");
		ct.add("hello");
		ct.add("hello");
		// System.err.println(ct);
		Collection<String> ct2 = new ArrayList<>();
		ct2.add("world");
		ct2.add("78910");
		ct.addAll(ct2);
		// System.out.println(ct);

		Iterator<String> iterator = ct.iterator();
		while (iterator.hasNext()) {
			System.err.println(iterator.next() + "；");
		}
	}
}
