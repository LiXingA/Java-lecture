package 第十四周上机;

public class _2 {
	public static void main(String[] args) {
		int a[] = { 2, 55, 23, 43 };
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length - i - 1; j++) {
				int 临时存储交换的变量;
				if (a[j] > a[j + 1]) {
					临时存储交换的变量 = a[j];
					a[j] = a[j + 1];
					a[j + 1] = 临时存储交换的变量;
				}
			}
		}
		for (int i : a) {
			System.out.print(i + " ");
		}
	}
}
