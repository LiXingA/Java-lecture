package 第八周代码;

public class IntegerTest {
	public static void main(String args[]) {
		String str = "123456789";
		char mychar = str.charAt(9);
		System.out.println(mychar);
	}

	

	public static void main8(String[] args) {
		String b = "2 > 1";
		Boolean boolean1 = new Boolean(b);
		System.err.println(boolean1);
	}

	// 8-4
	public static void main7(String[] args) {
		Character character1 = new Character('H');
		Character character2 = new Character('h');
		if (character1.equals(character2)) {
			System.err.println("内容相同");
		} else {
			System.err.println("内容不相同");
		}
		Character lowerCase1 = Character.toLowerCase(character1);
		Character lowerCase2 = Character.toLowerCase(character2);
		if (lowerCase1.equals(lowerCase2)) {
			System.err.println("转小写后内容相同");
		} else {
			System.err.println("转小写后内容不相同");
		}
	}

	// 8-3
	public static void main6(String[] args) {
		Integer integer = new Integer(70000);
		System.err.println(integer.intValue());
		// System.err.println(integer.doubleValue());
		// System.err.println(integer.byteValue());
	}

	public static void main5(String[] args) {
		Character character1 = new Character('a');
		Character character2 = new Character('a');
		Character.isUpperCase('A');
		Character.toUpperCase('a');

	}

	public static void main4(String[] args) {
		Boolean boolean1 = new Boolean(1 > 2);
		System.err.println(Boolean.FALSE);
		System.err.println(Boolean.TRUE);

		Number number = new Byte((byte) 123);
	}

	public static void main3(String[] args) {
		Double double1 = new Double(3.1415926);
		System.out.println(Double.toHexString(3.1415926));
		System.err.println(double1.intValue());

		double1 = new Double(1 / 0);
		System.err.println(double1);

	}

	public static void main2(String[] args) {
		int i = 123;
		Integer integer1 = new Integer(123);
		Integer integer2 = new Integer(456);
		integer1.compareTo(integer2);
		System.err.println(Integer.MAX_VALUE);
		System.err.println(Integer.MIN_VALUE);
		System.err.println(Integer.SIZE);
		System.err.println("八进制：" + Integer.toOctalString(123));
		System.err.println("十六进制：" + Integer.toHexString(123));
	}
}
