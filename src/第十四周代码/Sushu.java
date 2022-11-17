package 第十四周代码;

public class Sushu {
	public static void main(String[] args) {
		System.out.println("1000以内的素数为：");
		for (int i = 2; i < 1000; i++) {
			boolean isPrime = true;
			int k = (int) Math.sqrt(i);
			for (int j = 2; j <= k; j++) {
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				System.out.print(i + " ");
			}
		}
	}
}