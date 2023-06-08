package student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import student.excel.WkCalc;

public class ConfigData {
	private static ConfigData instance = null;

	public static ConfigData getInstance() {
		if (instance == null) {
			instance = new ConfigData();
		}
		return instance;
	}

	private String clazzsPath = null;

	private ConfigData() {
		try {
			File file = new File("./config.xlsx");
			Workbook wookbook = WkCalc.getWookbook(file);
			Sheet sheetAt = wookbook.getSheetAt(0);
			// 班级名称 课程名称 作业路径 网课记录路径
			int rownum = sheetAt.getFirstRowNum() + 1;
			Row firstRow = sheetAt.getRow(sheetAt.getFirstRowNum());
			Row row = sheetAt.getRow(rownum);
			this.clazzsPath = row.getCell(0).getStringCellValue();
			wookbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getClazzsPath() {
		return clazzsPath;
	}

	public static int BASE_RATE = 40;
	public static int ZY_RATE = 25;
	public static int CHECK_RATE = 6;
	public static int CHAT_RATE = 1;
	public static boolean DEFAULT_FIRST = true;
	public static boolean DEFAULT_DING = false;
	public static int MAX_CHECK_TIMES = 0;
	public static int MAX_CHAT_TIMES = 0;
	public static int MAX_ZY_TIMES = 0;
	public static String EXPORT_FILE_PATH = "./aaa.csv";
}
