package student.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

	public static boolean isHan(String a) {
		return a.matches("[\u4E00-\u9FA5\u9FA6-\u9FBB\uFF00-\uFFEF]");
	}

	public static void main(String[] args) {
		System.out.println("20计网你好".replaceAll("20计网", ""));
		String oldReg = "(（?机电一体化[4四]?班～?）?)|(22级)|(机电22)|(机电[4四]?班?)";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < oldReg.length(); i++) {
			String cur = oldReg.substring(i, i + 1);
			if (isHan(cur)) {
				builder.append("\\u").append(charToHex(cur.charAt(0)));
			} else {
				builder.append(cur.charAt(0));
			}
		}
		System.out.println(builder);
		System.out.println("机电22陈星".replaceAll(oldReg, ""));
		System.out.println("机电22陈星".replaceFirst(builder.toString(), ""));
		System.out.println("机电22陈星".replace("\u673a\u7535\u4e00\u4f53\u5316", ""));
	}

	static public String charToHex(char c) {
		// Returns hex String representation of char c
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}

	/**
	 * Converts a byte to the string representation of its hex value.
	 * 
	 * @param b The byte to process.
	 * @return A string consisting of hex digits.
	 */
	static public String byteToHex(byte b) {
		// Returns hex String representation of byte b
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static final SimpleDateFormat simpleDateFormatXie = new SimpleDateFormat("yyyy/MM/dd");

	public static Map<String, WkRecord> calcWk(String path, List<String> names, String errorNameReg) {
		Set<String> errorNameSet = new HashSet<>();
		Map<String, WkRecord> studentRecordByName = new LinkedHashMap<>();
		HandleFileI handler = new HandleFileI() {
			@Override
			public void handleFile(File file) throws Exception {
				if (file.getName().indexOf("签到") != -1) {
					Elements htmlTable = WkCalc.getHtmlTableBody(file);
					for (int i = 1; i < htmlTable.size(); i++) {
						Element element = htmlTable.get(i);
						Elements allElements = element.children();
						String oldName = allElements.get(0).text().trim();
						String name = null;
						if (names.indexOf(oldName) != -1) {
							name = oldName;
						} else {
							for (String string : names) {
								if (oldName.indexOf(string) != -1) {
									name = string;
									errorNameSet.add(String.format("%s -> %s OK", oldName, name));
									break;
								}
							}
							if (name == null) {
								if (!StringUtils.isBlank(errorNameReg)) {
									name = oldName.replaceAll(errorNameReg, "");// 指定规则过滤
								} else {
									name = oldName;
								}
								name = name.replaceAll("[^\u4E00-\u9FA5\u9FA6-\u9FBB]", "").trim();// 过滤非中文
								errorNameSet.add(String.format("%s -> %s %s", oldName, name,
										names.indexOf(name) != -1 ? "OK" : "NO"));
							}
						}
						// String name = oldName.trim().replaceAll("\\p{Alnum}|\\p{Punct}|\\s", "");
						String checkTime = allElements.get(2).text().trim();
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
		System.err.format("错误的名字：%s\n", StringUtils.join(errorNameSet, "\n"));
		return studentRecordByName;
	}
}
