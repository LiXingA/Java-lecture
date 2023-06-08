package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import student.excel.ChatInfo;
import student.excel.WkCalc;
import student.excel.WkRecord;

public class CheckZuoyeUtil {

	static class HandleFile implements HandleFileI {
		List<String> names;
		Map<String, List<Record>> studentRecordByName;
		private File rfile;
		private Map<File, List<File>> fileMap = new HashMap<>();
		private Map<File, Record> removeRecordMap = new HashMap<>();
		private StringBuilder builder = new StringBuilder();

		HandleFile(List<String> names, Map<String, List<Record>> studentRecordByName, File rfile) {
			this.names = names;
			this.studentRecordByName = studentRecordByName;
			this.rfile = rfile;
			if (this.rfile.exists() && null != this.rfile.listFiles()) {
				for (File f : this.rfile.listFiles()) {
					ArrayList<File> arrayList = new ArrayList<>();
					if (f.exists() && null != f.listFiles()) {
						for (File sf : f.listFiles()) {
							arrayList.add(sf);
						}
						fileMap.put(f, arrayList);
					}
				}
			}
		}

		File getRootFile(File file, Set<File> set) {
			if (set.contains(file)) {
				return file;
			}
			File p = file.getParentFile();
			while (p != null) {
				if (set.contains(p)) {
					return p;
				}
				p = p.getParentFile();
			}
			return null;
		}

		File removeFile(File file, List<File> list, Record record) {
			if (list.indexOf(file) != -1) {
				list.remove(file);
				removeRecordMap.put(file, record);
				return file;
			}
			File p = file.getParentFile();
			while (p != null) {
				if (list.indexOf(p) != -1) {
					list.remove(p);
					removeRecordMap.put(p, record);
					return p;
				}
				p = p.getParentFile();
			}
			return null;
		}

		public void handleFile(File file) throws Exception {
			for (String name : names) {
				if (file.getName().indexOf(name) != -1) {
					Record record = new Record(Types.file, file.getAbsolutePath(), name, file.lastModified());
					File root = getRootFile(file, fileMap.keySet());
					if (root == null) {
						throw new Exception("找不到根目录");
					}
					File r = removeFile(file, fileMap.get(root), record);
					if (r == null) {
						Record record2 = removeRecordMap.get(getRootFile(file, removeRecordMap.keySet()));
						if (!record2.getName().equals(name)) {
							builder.append("    ----- 学生" + name + ",复制了文件:交了文件" + file.getAbsolutePath() + "和"
									+ record2.getPath() + "重复提交");
							if (file.lastModified() < new File(record2.getPath()).lastModified()) {
								builder.append(",注：提交时间较前" + new Date(file.lastModified()) + ""
										+ new Date(new File(record2.getPath()).lastModified()) + "\n");
							}
							// return;
						}
					}
					studentRecordByName.get(name).add(record);
				}
			}
		}

		public void handleDir(File directory) throws Exception {
			for (String name : names) {
				if (directory.getName().indexOf(name) != -1) {
					Record record = new Record(Types.directory, directory.getAbsolutePath(), name,
							directory.lastModified());
					File root = getRootFile(directory, fileMap.keySet());
					if (root == null) {
						throw new Exception("找不到根目录");
					}
					File r = removeFile(directory, fileMap.get(root), record);
					if (r == null) {
						Record record2 = removeRecordMap.get(getRootFile(directory, removeRecordMap.keySet()));
						if (!record2.getName().equals(name)) {
							builder.append("    ----- 学生" + name + ", 复制了目录:交了目录" + directory.getAbsolutePath() + "和 "
									+ record2.getPath() + ",重复提交");
							if (directory.lastModified() < new File(record2.getPath()).lastModified()) {
								builder.append(",注：提交时间较前" + new Date(directory.lastModified()) + ""
										+ new Date(new File(record2.getPath()).lastModified()) + "\n");
							}
							// return;
						}
					}
					studentRecordByName.get(name).add(record);
				}
			}
		}
	};

	public static Details genDatas(ClazzConfig clazzConfig) throws IOException {
		Map<String, List<Record>> studentRecordByName = new LinkedHashMap<>();

		List<String> names = clazzConfig.getNames();// 我们班所有的学生姓名
		Map<String, WkRecord> wkMapByName = WkCalc.calcWk(clazzConfig.getWkPath(), names, clazzConfig.getErrNameReg());
		ArrayList<Object[]> arrayList = new ArrayList<>();
		String zuoyePath = clazzConfig.getZuoyePath();
		File zyFile = new File(zuoyePath);
		// if (!zyFile.exists()) {
		// Object[][] data = new Object[arrayList.size()][];
		// arrayList.toArray(data);
		// return new Details(data, wkMapByName, studentRecordByName);
		// }
		for (String name : names) {
			studentRecordByName.put(name, new ArrayList<Record>());
		}
		HandleFile handler = new HandleFile(names, studentRecordByName, zyFile);
		Tools.scanDirRecursion(zyFile, handler);
		System.out.println(handler.builder.toString());
		int maxCheckTimes = 0;
		int maxChatTimes = 0;
		int maxZyTimes = 0;
		for (Entry<String, List<Record>> entry : studentRecordByName.entrySet()) {
			String name = entry.getKey();
			List<Record> recordList = entry.getValue();
			WkRecord wkRecord = wkMapByName.get(name);
			List<ChatInfo> chats = wkRecord != null ? wkRecord.getChats() : new ArrayList<ChatInfo>();
			List<Date> checks = wkRecord != null ? wkMapByName.get(name).getChecks() : new ArrayList<Date>();
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
			firstRecord.setWangChats(chats);
			firstRecord.setWangChecks(checks);
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
			String errNameReg = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null;
			if (!StringUtils.isBlank(name) && !StringUtils.isBlank(lecture) && !names.isEmpty()) {
				list.add(new ClazzConfig(name, lecture, zuoyePath, wkPath, names, errNameReg));
			}
		}
		wookbook.close();
		return list.toArray(new ClazzConfig[list.size()]);
	}
}
