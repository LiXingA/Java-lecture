package 第七周代码;

import java.io.IOException;
import java.util.Scanner;

public class MathTest {
	public static void main(String[] args) {
		String str = "123456789";
		char mychar = str.charAt(9);
		System.out.println(mychar);

	}
	
	public static void main2(String[] args) throws IOException {
		System.out.println("请输入半径：");
		Scanner scanner = new Scanner(System.in);
		double r = scanner.nextDouble();
		double s = Math.PI * r * r;
		"adsds".length();
		
		System.err.println(String.format("圆的面积：%.2f", s));
		scanner.close();
	}
	
	
}
