package 第七周代码;

import java.util.Scanner;

public class ScannerTest {
	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请选择：");
		System.out.println("1：退出系统");
		System.out.println("2：进入游戏");
		int pwd = scanner.nextInt();
		switch (pwd) {
		case 1:
			System.err.println("关机中。。。");
			break;
		case 2:
			System.out.println("游戏进行中。。。");
			Thread.sleep(10000);
			System.err.println("游戏结束");
			break;
		default:
			break;
		}
		scanner.close();
	}
}
