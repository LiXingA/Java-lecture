package 第九周上机;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionTest {
	public static void main(String[] args) throws IOException {
		Collection list = new ArrayList<>();
		FileReader fr = new FileReader("./19计网");
		BufferedReader br = new BufferedReader(fr);
		String line;// 一行数据
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		fr.close();
		System.err.println(list);

	}
}
