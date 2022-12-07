package exam;

import java.util.Scanner;

public class test2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入x的值：");
        int x = scan.nextInt();
        if (x % 2 == 0) {
            System.out.format("x=%d是%s", x, "偶数");
        } else {
            System.out.format("x=%d是%s", x, "奇数");
        }
    }
}
