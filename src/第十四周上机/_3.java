package 第十四周上机;

import java.util.Arrays;
import java.util.Scanner;

public class _3 {
	public static void main(String[] args) {
		int year = 2000;
		int month = 3;
		int date = 1;
		int 月份天数[];
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 四年一闰，百年不闰，四百年再闰
			月份天数 = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		} else {
			月份天数 = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		}
		int 这一年第几天 = 0;
		for (int i = 0; i < month - 1; i++) {
			这一年第几天 += 月份天数[i];
		}
		这一年第几天 += date;
		System.out.println("这一年第几天：" + 这一年第几天);

		Scanner scanner = new Scanner(System.in);
		int[] arr = new int[] { 1, 23, 3, 2 };
		Arrays.sort(arr);
		for (int i : arr) {
			System.out.print(i + " ");
		}
		scanner.close();
	}
}
