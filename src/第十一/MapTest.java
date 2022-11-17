package 第十一;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapTest {
	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		map.put(123, "张三");
		map.put(124, null);
		for (Entry<Integer, String> string : map.entrySet()) {
			System.err.println(string);
		}
	}
	
	public static void main3(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("5#138(1)", "张三");
		map.put("5#440(5)", "李四");
		map.put("5#442(3)", "王伟");
		map.put("5#441(4)", "王伟");
		map.put("5#445(5)", "王伟");
		map.put("5#446(6)", "王伟");
		Object object = map.get("5#440(5)");
		System.err.println(object);
		for (Entry<String, String> keyValue : map.entrySet()) {
			System.out.println(keyValue);
		}
	}
}
