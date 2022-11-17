package 第十五周;

public class A {
	int a = 0;
	int b = 13;

	{
		a = 12 + b;
	}

	public static void main(String[] args) {
		A a2 = new A();
		System.out.println(a2.a);
	}
}
