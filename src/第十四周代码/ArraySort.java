package 第十四周代码;

public class ArraySort {
	public static void main(String[] args) {
		int[] numArray = { 1, 65, 32, 82, 16, 68 };
		for (int i = 0; i < numArray.length - 1; i++) {
			for (int j = i + 1; j < numArray.length; j++) {
				if (numArray[i] > numArray[j]) {
					int x = numArray[i];
					numArray[i] = numArray[j];
					numArray[j] = x;
				}
			}
		}
		for (int k = 0; k < numArray.length; k++) {
			System.out.print(numArray[k] + "\t");
		}
	}
}
