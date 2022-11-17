package 第十周代码;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListTest {

	public static void main(String[] args) {
		ArrayList list = new ArrayList();
		list.add(new Integer(1));
		list.add(2);
		list.add(5);
		list.add(2);
		list.add(1, new Integer(130));
		list.addAll(1, Arrays.asList(230, 190, 180));
		System.err.println(list);
		List subList = list.subList(1, 5);
		System.out.println(subList);
		subList.add(123);
		System.out.println(subList);
		System.err.println(list);
//		Iterator iterator = list.iterator();
//		int index=0;
//		while(iterator.hasNext()) {
//			System.err.println("下标"+(index++) +":"+iterator.next());
//		}
//		for (Object object : list) {
//			System.err.println(object);
//		}
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
//		for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
//			Object object = (Object) iterator2.next();
//			
//		}
//		System.out.println(list.toString());
		// Object object = list.get(1);
		// System.out.println(object);
		// Object remove = list.remove(2);
		// System.err.println(remove);
		// int indexOf = list.indexOf(5);
		// System.err.println("5在链表的位置为：" + indexOf);

	}
}
