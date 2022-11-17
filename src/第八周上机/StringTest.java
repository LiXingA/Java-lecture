package 第八周上机;

public class StringTest {
	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4, 5, 6 };
		String jieguo = 数组转字符串2(arr);
		System.out.println(jieguo);
	}

	public static String 数组转字符串2(int[] arr) {
		StringBuffer string = new StringBuffer("[");
		for (int index = 0; index < arr.length; index++) {
			if (index == 0) {// 首个数据
				string.append("word");
			} else {
				string.append("#word");
			}
			string.append(arr[index]);
		}
		string.append("]");// 结尾
		return string.toString();
	}

	public static String 数组转字符串(int[] arr) {
		String string = "[";
		for (int index = 0; index < arr.length; index++) {
			if (index == 0) {// 首个数据
				string += "word";
			} else {
				string += "#word";
			}
			string += arr[index];
		}
		string += "]";// 结尾
		return string;
	}
}
