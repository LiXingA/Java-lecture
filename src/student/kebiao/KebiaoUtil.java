package student.kebiao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import student.excel.FileProcessor;
import student.excel.WkCalc;

public class KebiaoUtil {

    static void createCell(Sheet sheet, String excelTag, String value) throws Exception {
        Pattern p = Pattern.compile("^([A-Z]+)(\\d+)$");
        Matcher matcher = p.matcher(excelTag.toUpperCase());
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            int cellnum = matcher.group(1).charAt(0) - "A".charAt(0);
            int rownum = Integer.parseInt(matcher.group(2)) - 1;
            createCell(sheet, rownum, cellnum, value);
        } else {
            throw new Exception("row error!");
        }
    }

    private static void createCell(Sheet sheet, int rownum, int cellnum, String value) {
        Row row = sheet.getRow(rownum);
        Cell cell = row.getCell(cellnum);
        if (cell == null) {
            cell = row.createCell(cellnum, CellType.STRING);
        }
        cell.setCellValue(value);
    }

    private static class LectureRecord {
        private final int rowNum;
        private final int columnNum;

        public LectureRecord(int rowNum, int columnNum) {
            this.rowNum = rowNum;
            this.columnNum = columnNum;
        }

        public int getRowNum() {
            return rowNum;
        }

        public int getColumnNum() {
            return columnNum;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> appendMap = new HashMap<>();
        File fromFile = new File(
                "D:\\graph\\tmp\\observable\\projects\\63e84608c15916ae26c5c043\\excel\\files\\下埠课表212.xlsx");
        // File toFile = new
        // File("C:\\Users\\Xing\\Desktop\\2022-2023学年20、21、22级第二学期课表+周历暂定稿0212
        // (下埠教学点)(1).xlsx");
        File toFile = new File(
                "D:\\graph\\tmp\\observable\\projects\\63e84608c15916ae26c5c043\\excel\\files\\2022-2023学年20、21、22级第二学期课表+周历暂定稿0212 (下埠教学点).xlsx");
        Workbook fromWookbook = WkCalc.getWookbook(fromFile);
        Workbook toWookbook = WkCalc.getWookbook(toFile);
        Sheet fromSheet = fromWookbook.getSheetAt(0);
        createCell(fromSheet, "V13", "HTML5网页制作\n李星\n践行楼104");
        createCell(fromSheet, "L16", "Protel Dxp1\n钟子良\n践行楼102");
        createCell(fromSheet, "Z16", "C语言程序设计\n钟子良\n践行楼101");
        Sheet toSheet = toWookbook.getSheetAt(0);
        Sheet nameSheet = toWookbook.getSheet("班级对应名称");
        Map<String, String> nameMap = new HashMap<>();
        for (int rownum = nameSheet.getFirstRowNum(); rownum <= nameSheet.getLastRowNum(); rownum++) {
            Row row = nameSheet.getRow(rownum);
            Cell sysCell = row.getCell(0);
            Cell boastCell = row.getCell(1);
            if (sysCell == null) {
                break;
            }
            nameMap.put(sysCell.getStringCellValue(), boastCell != null ? boastCell.getStringCellValue() : "");
        }
        List<Integer> ss = Arrays.asList(7, 9);
        Row fromTitleRow = fromSheet.getRow(fromSheet.getFirstRowNum() + 2);
        int minus = 1;
        List<HashMap<String, LectureRecord>> lectures = new ArrayList<>();
        for (int i = 0; i < 1 + 4 * 4 + 2; i++) {
            lectures.add(new HashMap<>());
        }
        int wdCounts[] = new int[1 + 4 * 4 + 2];
        Arrays.fill(wdCounts, 1);
        for (int rownum = fromSheet.getFirstRowNum() + 3; rownum <= fromSheet.getLastRowNum(); rownum++) {
            Row fromRow = fromSheet.getRow(rownum);
            int toRownum = rownum - minus;
            Row toRow = toSheet.getRow(toRownum);
            String className = fromRow.getCell(0) != null ? fromRow.getCell(0).getStringCellValue() : "";
            if (StringUtils.isBlank(className)) {
                break;
            }
            String boastName = nameMap.get(className);
            if (StringUtils.isBlank(boastName)) {
                minus++;
                continue;
            }
            System.err.println(className + "-->" + boastName);
            Cell toFirstCell = toRow.getCell(0);
            toFirstCell.setCellValue(boastName);
            for (int i = 0; i < 4 * 5 + 2; i++) {
                int cellnum = i * 2 + 1;
                Cell numCell = fromTitleRow.getCell(cellnum);
                int toCellnum = i + 1 - (i / 5);
                String lecture = fromRow.getCell(cellnum) != null ? fromRow.getCell(cellnum).getStringCellValue() : "";
                if (StringUtils.isBlank(lecture) && numCell != null
                        && ss.indexOf((int) numCell.getNumericCellValue()) != -1) {
                    continue;
                }
                String nowLecture;
                if (StringUtils.isNotBlank(lecture)) {
                    HashMap<String, LectureRecord> lectureM = lectures.get(toCellnum);
                    nowLecture = lecture.replace("习近平新时代中国特色社会主义思想概论", "习近平思想概论").replaceAll("（.*）|五年制|五年", "");
                    if (nowLecture.startsWith("体育")) {
                        nowLecture += "教室未定" + wdCounts[toCellnum]++;
                    }
                    if (lectureM.containsKey(lecture)) {
                        int insertIndex = nowLecture.lastIndexOf("\n") + 1;
                        nowLecture = nowLecture.substring(0, insertIndex) + "合" + nowLecture.substring(insertIndex);
                        LectureRecord lectureRecord = lectureM.get(lecture);
                        appendMap.remove("r" + lectureRecord.getRowNum() + "c" + lectureRecord.getColumnNum());
                        createCell(toSheet, lectureRecord.getRowNum(), lectureRecord.getColumnNum(), nowLecture);
                    }
                    lectureM.put(lecture, new LectureRecord(toRownum, toCellnum));
                    System.out.println(lecture.replace("\n", " ") + "->" + nowLecture.replace("\n", " "));
                } else {
                    nowLecture = "";
                }
                Cell toCell = toRow.getCell(toCellnum);
                if (toCell == null) {
                    toCell = toRow.createCell(toCellnum, CellType.STRING);
                }
                if (!toCell.getStringCellValue().equals(nowLecture)) {
                    appendMap.put("r" + toRownum + "c" + toCellnum,
                            new StringBuilder().append(toCell.getStringCellValue().replace("\n", " ")).append(" => ")
                                    .append(nowLecture.replace("\n", " ")).toString());
                }
                toCell.setCellValue(nowLecture);
            }
        }
        File outFile = new File("./Kebiao.txt");
        StringBuilder builder = new StringBuilder();
        builder.append(new Date().toString()).append("\n");
        for (Entry<String, String> i : appendMap.entrySet()) {
            builder.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
        }
        FileProcessor.append(outFile.toPath(), builder.toString());
        toWookbook.write(new FileOutputStream(toFile));
    }
}