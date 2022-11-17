package student.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import student.HandleFileI;
import student.HandleFileI.Tools;

public class WkCalc {
	// D:\Student\计算机网络\record
	// D:\Student\excel\record
	// D:\Student\Java\record
	// D:\Student\应用基础\record
	public static Workbook getWookbook(File file) throws FileNotFoundException, IOException {
		Workbook wk;
		if (!file.exists()) {
			return null;
		}
		if (file.getName().endsWith(".xls")) {
			try {
				wk = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception e) {
				System.err.format("error: %s ,file: %s\n", e.getMessage(), file.getName());
				wk = null;
			}
		} else if (file.getName().endsWith(".xlsx")) {
			wk = new XSSFWorkbook(new FileInputStream(file));
		} else {
			return null;
		}
		return wk;
	}

	public static Elements getHtmlTableBody(File file) throws IOException {
		String readAllLines = FileProcessor.readAllLines(new FileInputStream(file));
		Document html = Jsoup.parse(readAllLines);
		return html.body().children().get(0).children().get(0).children();
	}

	static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
	static final SimpleDateFormat simpleDateFormatXie = new SimpleDateFormat("YYYY/MM/dd");

	public static Map<String, WkRecord> calcWk(String path, List<String> names) {
		Map<String, WkRecord> studentRecordByName = new LinkedHashMap<>();
		HandleFileI handler = new HandleFileI() {
			@Override
			public void handleFile(File file) throws Exception {
				if (file.getName().indexOf("签到") != -1) {
					Elements htmlTable = WkCalc.getHtmlTableBody(file);
					for (int i = 1; i < htmlTable.size(); i++) {
						Element element = htmlTable.get(i);
						Elements allElements = element.children();
						String name = allElements.get(0).text();
						String checkTime = allElements.get(2).text();
						WkRecord record = studentRecordByName.get(name);
						if (record == null) {
							record = new WkRecord();
							studentRecordByName.put(name, record);
						}
						record.getChecks().add(simpleDateFormat.parse(checkTime));
					}
				}
				if (file.getName().indexOf("聊天记录") != -1) {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
					String line;
					Date dateFirstLine = null;
					while ((line = bufferedReader.readLine()) != null) {// 是否文件读完
						if (dateFirstLine == null) {
							dateFirstLine = simpleDateFormatXie.parse(line);
						}
						String info = line.trim();
						for (String name : names) {
							if (info.contains(name)) {
								WkRecord record = studentRecordByName.get(name);
								if (record == null) {
									record = new WkRecord();
									studentRecordByName.put(name, record);
								}
								record.getChats().add(new ChatInfo(dateFirstLine, info));
							}
						}
					}
					bufferedReader.close();
				}
			}

			@Override
			public void handleDir(File directory) throws Exception {

			}
		};
		Tools.scanDirRecursion(new File(path), handler);
		return studentRecordByName;
	}
}
