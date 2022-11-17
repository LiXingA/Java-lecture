package 第六周代码;

import org.apache.jena.sparql.pfunction.library.str;

public class StringTest {

	class 内部类 {
		private final int _ID;

		public 内部类(int _ID) {
			super();
			this._ID = _ID;
		}

		@Override
		public String toString() {
			return "我这个内部类对象的身份证号码是" + _ID;
		}
	}

	public static void main(String[] args) {
		StringTest stringTest = new StringTest();
		内部类 inner = stringTest.new 内部类(123);
		System.out.println(inner.toString());
		System.out.println(inner);
	}

	public static void main11(String[] args) {
		System.out.println(String.format("数字%d", 1234));
		System.out.println(String.format("小数：%10.2f", 3.14159265));
		System.err.println(String.format("%cbbbb%saaaa%c", '中', "字符串", '$'));
	}

	public static void main10(String[] args) {
		String string = "abcbsadsb";
		System.err.println(string);
		String[] split = string.split("b");
		for (String string2 : split) {
			System.out.println(string2);
		}
	}

	public static void main9(String[] args) {
		String str = "鱼戏莲叶间，莲叶何田田";
		System.out.println(str.replace("莲", "荷"));
		String replaceAll = "abc\nb Ac\td".replaceAll("\\s", "空白");
		System.err.println(replaceAll);
	}

	public static void main8(String[] args) {
		String str = " admin \n\r\n\t";
		System.out.println(str.trim());
	}

	public static void main7(String[] args) {
		String str = "NBA player\nKobe is my favorite player";
		System.out.println(str.substring(11, 15));
	}

	public static void main6(String[] args) {
		String str = "NBA player\nKobe is my favorite player";
		System.out.println(str);
		System.out.println(str.indexOf('a', 10));
		System.out.println(str.lastIndexOf('a'));
		// System.out.println(str.lastIndexOf("player"));
		// System.out.println(str.indexOf("is"));
	}

	public static void main5(String[] args) {
		String str = "NBA Player\nKobe is my favorite player";
		System.out.println(str.length());
		System.out.println(str.toUpperCase());
		System.out.println(str.toLowerCase());
	}

	public static void main4(String[] args) {
		String str1 = "bbc";
		String str2 = "Bcd";
		int compareTo = str1.compareTo(str2);
		if (compareTo > 0) {
			System.out.println(str1 + "大于" + str2);
		} else if (compareTo < 0) {
			System.out.println(str1 + "小于" + str2);
		} else {
			System.out.println(str1 + "等于" + str2);
		}

	}

	public static void main3(String[] args) {
		String str1 = new String("学生abc");
		// String str2 = "学生bcd";
		// if (str1.endsWith("abc")) {//以什么结尾
		// } else {
		// }

	}

	public static void main2(String[] args) {
		String str1 = new String("ABC");
		String str2 = "abc";
		if (str1.equalsIgnoreCase(str2)) {
			System.out.println("不区分大小写，判断内容一样");
		} else {
			System.out.println("不区分大小写，判断内容不同");
		}

	}
}
