package 第十一;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiwanTest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(100000);
		for (int i = 0; i < 100000; i++) {
			int ran = (int) (Math.random() * 1000);
			list.add(ran);
		}
		Set<Integer> set = new HashSet<>();
		for (Integer integer : list) {
			if (set.contains(integer)) {
				System.err.println("第一次出现的位置：" + list.indexOf(integer));
				System.err.println("第一个重复的数据" + integer + "位置在：" + set.size());
				break;
			}
			set.add(integer);
		}
		for (int i = 0; i <= set.size(); i++) {
			System.out.println("位置：" + i + ":" + list.get(i));
		}
	}
}
