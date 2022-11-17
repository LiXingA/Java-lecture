package 第十四周代码;

public class YunSuanTest {

	public static void main(String[] args) {
		int a =-1;
		for(int i=4;i>0;i--){
			a+=i;
			System.out.print(a);
		}

	}

	public void switchTest(byte c) {
		switch (c) {
		}
	}

	public static void main6(String[] args) {
		int 成绩 = 78;
		if (成绩 >= 90) {
			System.out.println("优秀");
		} else if (75 <= 成绩 && 成绩 < 90) {
			System.out.println("良");
		} else if (60 <= 成绩 && 成绩 < 75) {
			System.out.println("及格");
		} else {
			System.out.println("不及格");
		}
	}

	public static void main5(String[] args) {
		String aString = "abc";
		System.out.println(aString instanceof String);
	}

	public static void main4(String[] args) {

		System.out.println((0b0101 ^ 0b1010) == 0b1111);
		// System.out.println(2>1&&3>2);
	}

	public static void main3(String[] args) {
		char a = 65;
		System.out.println(a);
		System.out.println('a' == 97);
		System.out.println('A' == 65);
	}

	public static void main2(String[] args) {
		for (char ch = 'a'; ch <= 'z'; ch++) {
			System.out.print(ch + " ");
		}
		System.out.println();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			System.out.print((int) ch + " ");
		}
		System.out.println();
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			System.out.print(ch);
		}
		System.out.println();

		for (char ch = 'A'; ch <= 'Z'; ch++) {
			System.out.print((int) ch + " ");
		}

		// System.out.println(7.5 / 0);
		// System.out.println(-7.5 / 0);
		// System.out.println(7.5 % 0);
		// System.out.println(-7.5 % 0);
		// System.out.println(0 / 5);
		// System.out.println(0 % 5);
		// System.out.println(0 / 0);
		// System.out.println(0 % 0);
	}
}
