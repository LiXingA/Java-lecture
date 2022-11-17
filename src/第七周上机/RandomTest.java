package 第七周上机;

import java.util.Scanner;

public class RandomTest {
	public static void main(String[] args) {
		int M = (int) (Math.random() * 100);
		// System.err.println(M);
		Scanner scanner = new Scanner(System.in);
		int N;
		for (N = scanner.nextInt();; N = scanner.nextInt()) {
			if (N > M) {
				System.err.println("你猜大了！");
			} else if (N < M) {
				System.err.println("你猜小了！");
			} else {
				System.err.println("恭喜你，你猜对了！");
				System.err.printf("%d == %d", N, M);
				break;
			}
		}
		scanner.close();
	}
}
