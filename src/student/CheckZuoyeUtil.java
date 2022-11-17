package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import student.HandleFileI.Tools;
import student.excel.WkCalc;
import student.excel.WkRecord;

public class CheckZuoyeUtil {

	public static Details genDatas(ClazzConfig clazzConfig) throws IOException {
		List<String> names = clazzConfig.getNames();// 我们班所有的学生姓名
		String zuoyePath = clazzConfig.getZuoyePath();
		Map<String, List<Record>> studentRecordByName = new LinkedHashMap<>();
		for (String name : names) {
			studentRecordByName.put(name, new ArrayList<Record>());
		}
		HandleFileI handler = new HandleFileI() {
			public void handleFile(File file) throws Exception {
				for (String name : names) {
					if (file.getName().indexOf(name) != -1) {
						// System.err.println("学生 " + name + ":交了文件" + file.getName());
						studentRecordByName.get(name)
								.add(new Record(Types.file, file.getAbsolutePath(), name, file.lastModified()));
					}
				}
			}

			public void handleDir(File directory) throws Exception {
				for (String name : names) {
					if (directory.getName().indexOf(name) != -1) {
						// System.err.println("学生 " + name + ":交了目录" + directory.getName());
						Record record = new Record(Types.directory, directory.getAbsolutePath(), name,
								directory.lastModified());
						studentRecordByName.get(name).add(record);
					}
				}
			}
		};
		Tools.scanDirRecursion(new File(zuoyePath), handler);
		Map<String, WkRecord> wkMapByName = WkCalc.calcWk(clazzConfig.getWkPath(), names);
		ArrayList<Object[]> arrayList = new ArrayList<>();
		int maxCheckTimes = 0;
		int maxChatTimes = 0;
		int maxZyTimes = 0;
		for (Entry<String, List<Record>> entry : studentRecordByName.entrySet()) {
			String name = entry.getKey();
			List<Record> recordList = entry.getValue();
			WkRecord wkRecord = wkMapByName.get(name);
			int chatTimes = wkRecord != null ? wkRecord.getChats().size() : 0;
			int checkTimes = wkRecord != null ? wkMapByName.get(name).getChecks().size() : 0;
			Set<String> set = new HashSet<>();
			StringBuilder builder = new StringBuilder();
			if (!recordList.isEmpty()) {
				for (Record record : recordList) {
					String nextPath = record.getPath().substring(zuoyePath.length() + 1);
					if (nextPath.indexOf(File.separator) != -1)
						record.setWeekIndex(nextPath.substring(0, nextPath.indexOf(File.separator)));
					if (!set.add(record.getWeekIndex())) {
						if (builder.length() > 0)
							builder.append("、");
						builder.append(record.getWeekIndex());
					}
				}

			}
			Record firstRecord = recordList.size() == 0 ? new Record(Types.nosubmit, "没交", name, 0l)
					: new Record(Types.details, "详情", name, 0l);
			recordList.add(0, firstRecord);
			firstRecord.setFirst(true);
			firstRecord.setTimes(set.size());
			firstRecord.setRepeatTimes(builder.toString());
			firstRecord.setwChatTimes(chatTimes);
			firstRecord.setwChecks(checkTimes);
			if (firstRecord.getTimes() > maxZyTimes) {
				maxZyTimes = firstRecord.getTimes();
			}
			if (firstRecord.getwChatTimes() > maxChatTimes) {
				maxChatTimes = firstRecord.getwChatTimes();
			}
			if (firstRecord.getwChecks() > maxCheckTimes) {
				maxCheckTimes = firstRecord.getwChecks();
			}
		}

		ConfigData.MAX_ZY_TIMES = maxZyTimes;
		ConfigData.MAX_CHECK_TIMES = maxCheckTimes;
		ConfigData.MAX_CHAT_TIMES = maxChatTimes;

		for (Entry<String, List<Record>> entry : studentRecordByName.entrySet()) {
			List<Record> recordList = entry.getValue();
			for (Record record : recordList) {
				arrayList.add(record.toDatas());
			}
		}

		Object[][] data = new Object[arrayList.size()][];
		arrayList.toArray(data);
		return new Details(data, wkMapByName, studentRecordByName);
	}

	public static void openDirectory(String path) {
		String temp = path;
		File file = new File(temp);
		if (!file.exists()) {
			return;
		}
		try {
			Runtime.getRuntime().exec("cmd /c start explorer \"" + file.getCanonicalPath() + "\"");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void openFile(String path) {
		String temp = path;
		File file = new File(temp);
		if (!file.exists()) {
			return;
		}
		try {
			java.awt.Desktop.getDesktop().open(file);// 打开文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ClazzConfig[] getClazzConfigs() throws FileNotFoundException, IOException {
		List<ClazzConfig> list = new ArrayList<>();
		File file = new File(ClazzConfig.getClazzsPath());
		Workbook wookbook = WkCalc.getWookbook(file);
		Sheet sheetAt = wookbook.getSheetAt(0);
		// 班级名称 课程名称 作业路径 网课记录路径
		for (int rownum = sheetAt.getFirstRowNum() + 1; rownum <= sheetAt.getLastRowNum(); rownum++) {
			Row row = sheetAt.getRow(rownum);
			String name = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
			Sheet sheet = wookbook.getSheet(name);
			List<String> names = new ArrayList<>();
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row2 = sheet.getRow(i);
				String stringCellValue = row2.getCell(0).getStringCellValue();
				if (!StringUtils.isBlank(stringCellValue)) {
					names.add(stringCellValue);
				}
			}
			String lecture = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
			String zuoyePath = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
			String wkPath = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";
			if (!StringUtils.isBlank(name) && !StringUtils.isBlank(lecture) && !names.isEmpty()) {
				list.add(new ClazzConfig(name, lecture, zuoyePath, wkPath, names));
			}
		}
		return list.toArray(new ClazzConfig[list.size()]);
	}
}
