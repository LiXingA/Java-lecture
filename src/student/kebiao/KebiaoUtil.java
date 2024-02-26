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

/**
 * 课表转换工具
 */
public class KebiaoUtil {

    static void createCell(Sheet sheet, String excelTag, String value) throws Exception {
        Pattern p = Pattern.compile("^([A-Z]+)(\\d+)$");
        Matcher matcher = p.matcher(excelTag.toUpperCase());
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            int cellnum = toNumber(matcher.group(1)) - 1;
            int rownum = Integer.parseInt(matcher.group(2)) - 1;
            createCell(sheet, rownum, cellnum, value);
        } else {
            throw new Exception("row error!");
        }
    }

    public static int toNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length(); i++) {
            number = number * 26 + (name.charAt(i) - ('A' - 1));
        }
        return number;
    }

    public static String toName(int number) {
        StringBuilder sb = new StringBuilder();
        while (number-- > 0) {
            sb.append((char) ('A' + (number % 26)));
            number /= 26;
        }
        return sb.reverse().toString();
    }

    private static boolean createCell(Sheet sheet, int rownum, int cellnum, String value) {
        Row row = sheet.getRow(rownum);
        Cell cell = row.getCell(cellnum);
        if (cell == null) {
            cell = row.createCell(cellnum, CellType.STRING);
        }
        if (!cell.getStringCellValue().equals(value)) {
            cell.setCellValue(value != null && value.trim().equals("") ? null : value);
            return true;
        }
        return false;
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

    public final static int columEachCell = 5;

    public static void main(String[] args) throws Exception {
        Map<String, String> wzx = new HashMap<>();
        Map<String, String> appendMap = new HashMap<>();
        // 教务系统中导出来的课表
        File fromFile = new File(
                "./全校班级课表.xlsx");
        // 要导出来修改的课表
        File toFile = new File(// %projects/64e0cc56e429395ff60ad73d/excel/
                "D:\\graph\\tmp\\observable\\projects\\65d2f4b43e87af60c452c03e\\excel\\files\\2023-2024学年21、22、23级第二学期课表+周历暂定稿0225 (下埠教学点).xlsx");
        boolean createNewRow = false;// 转换数据是否是创建新的行
        Workbook fromWookbook = WkCalc.getWookbook(fromFile);
        Workbook toWookbook = WkCalc.getWookbook(toFile);
        Sheet fromSheet = fromWookbook.getSheetAt(0);
        // 针对部分老师的排课要求，如出现单双班课程、 特殊要修改的单元格
        createCell(fromSheet, "AF15", "Protel Dxp\n钟子良\n践行楼201");
        createCell(fromSheet, "AG15", "Protel Dxp\n钟子良\n践行楼201");
        createCell(fromSheet, "AF16", "Protel Dxp\n钟子良\n践行楼201");
        createCell(fromSheet, "AG16", "Protel Dxp\n钟子良\n践行楼201");
        Sheet toSheet = toWookbook.getSheetAt(0);
        Sheet toWzxShanKeSheet = toWookbook.getSheet("晚自习上课");
        if (toWzxShanKeSheet == null) {
            throw new Exception("晚自习上课");
        }
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
        List<Integer> ss;
        if (columEachCell == 4) {
            ss = Arrays.asList(7, 8, 9, 10);
        } else if (columEachCell == 5) {
            ss = Arrays.asList(9, 10);
        }
        Row fromTitleRow = fromSheet.getRow(fromSheet.getFirstRowNum() + 2);
        int minus = 1;
        List<HashMap<String, LectureRecord>> lectures = new ArrayList<>();
        for (int i = 0; i < 1 + 4 * columEachCell + 2; i++) {
            lectures.add(new HashMap<>());
        }
        int wdCounts[] = new int[1 + 4 * columEachCell + 2];
        Arrays.fill(wdCounts, 1);
        for (int rownum = fromSheet.getFirstRowNum() + 3; rownum <= fromSheet.getLastRowNum(); rownum++) {
            Row fromRow = fromSheet.getRow(rownum);
            String className = fromRow.getCell(0) != null ? fromRow.getCell(0).getStringCellValue() : "";
            if (StringUtils.isBlank(className)) {
                System.err.println("系统表里有错误！");
                break;
            }
            String boastName = nameMap.get(className);
            if (StringUtils.isBlank(boastName)) {// ！！！系统内的班级名称没有对应校区班级名称，则直接忽略掉这一行读取下一行
                minus++;
                continue;
            }
            int toRownum = -1;
            if (createNewRow) {// 创建新的一行数据
                toRownum = rownum - minus;
            } else {
                for (int i = toSheet.getFirstRowNum(); i <= toSheet.getLastRowNum(); i++) {// 找到现有行
                    if (toSheet.getRow(i).getCell(0) != null
                            && toSheet.getRow(i).getCell(0).getStringCellValue().equals(boastName)) {
                        toRownum = i;
                    }
                }
            }
            if (toRownum == -1) {
                System.err.println("表里有错误！找不到班级" + boastName);
                break;
            }
            Row toRow = toSheet.getRow(toRownum);
            Row toWzxRow = toWzxShanKeSheet.getRow(toRownum - 1);
            System.err.println(className + "-->" + boastName);
            Cell toFirstCell = toRow.getCell(0);
            toFirstCell.setCellValue(boastName);
            for (int i = 0; i < 4 * 5 + 2; i++) {
                int cellnum = i * 2 + 1;
                Cell numCell = fromTitleRow.getCell(cellnum);
                int toCellnum;
                if (columEachCell == 4) {
                    toCellnum = ((i + 1) % 5 == 0 ? i - 1 : i) + 1 - (i / 5);// !!!!!!!!!!系统表里是每天5节，因为下埠课表每天只显示4节，所以系统每天的最后1节特殊处理掉，放在每天倒数第二节
                } else if (columEachCell == 5) {
                    toCellnum = i + 1;
                }
                boolean iWzx = numCell != null
                        && ss.indexOf((int) numCell.getNumericCellValue()) != -1;
                String lecture = fromRow.getCell(cellnum) != null ? fromRow.getCell(cellnum).getStringCellValue() : "";
                // if (StringUtils.isBlank(lecture) && iWzx) {
                // 晚自习7-8/9-10节课如果为空，则直接忽略不复制到目标单元格
                // continue;
                // }
                String nowLecture;
                int dayIndex = (toCellnum - 1) / columEachCell;
                if (StringUtils.isNotBlank(lecture)) {
                    HashMap<String, LectureRecord> lectureM = lectures.get(toCellnum);
                    // ！！！名称太长，需要替换的文字
                    nowLecture = lecture
                            .replace("毛泽东思想和中国特色社会主义理论体系概论", "毛泽东思想概论")
                            .replace("习近平新时代中国特色社会主义思想概论（高中版）", "习近平思想概论")
                            .replaceAll("（.*）|五年制|五年", "");
                    if (nowLecture.startsWith("体育") || nowLecture.startsWith("大学体育")) {
                        nowLecture += "教室未定" + wdCounts[toCellnum]++;
                    }
                    if (lecture.contains("李玉洁")) {
                        System.err.println();
                    }
                    if (lectureM.containsKey(lecture)) {
                        int insertIndex = nowLecture.lastIndexOf("\n") + 1;
                        nowLecture = nowLecture.substring(0, insertIndex) + "合" + nowLecture.substring(insertIndex);
                        LectureRecord lectureRecord = lectureM.get(lecture);
                        appendMap.remove("r" + lectureRecord.getRowNum() + "c" + lectureRecord.getColumnNum());
                        if (iWzx) {
                            if (createCell(toWzxShanKeSheet, lectureRecord.getRowNum() - 1, dayIndex + 1, nowLecture)) {
                            }
                        } else {
                            if (createCell(toSheet, lectureRecord.getRowNum(), lectureRecord.getColumnNum(),
                                    nowLecture)) {
                            }
                        }
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
                if (iWzx) {
                    if (createCell(toWzxShanKeSheet, toRownum - 1, dayIndex + 1, nowLecture)) {
                        wzx.put("r" + toRownum + "c" + toCellnum,
                                new StringBuilder().append(boastName).append(" ").append("周")
                                        .append(Arrays.asList("一", "二", "三", "四", "五").get(dayIndex))
                                        .append(Arrays.asList("1-2节", "3-4节", "5-6节", "7-8节", "9-10节")
                                                .get((toCellnum - 1) % columEachCell))
                                        .append(" ").append((char) (toCellnum + 65) + "" + (toRownum + 1)).append(" ")
                                        .append(nowLecture.replace("\n", " ")).append("\n").toString());
                    }
                } else {
                    if (!toCell.getStringCellValue().equals(nowLecture)) {
                        appendMap.put("r" + toRownum + "c" + toCellnum,
                                new StringBuilder().append(boastName).append(" ").append("周")
                                        .append(Arrays.asList("一", "二", "三", "四", "五").get(dayIndex))
                                        .append(Arrays.asList("1-2节", "3-4节", "5-6节", "7-8节", "9-10节")
                                                .get((toCellnum - 1) % columEachCell))
                                        .append(" ").append((char) (toCellnum + 65) + "" + (toRownum + 1)).append(" ")
                                        .append(toCell.getStringCellValue().replace("\n", " ")).append(" => ")
                                        .append(nowLecture.replace("\n", " ")).toString());
                    }
                    toCell.setCellValue(nowLecture != null && nowLecture.trim().equals("") ? null : nowLecture);
                }
            }
        }
        File outFile = new File("./Kebiao.txt");
        StringBuilder builder = new StringBuilder();
        builder.append("++++++++++++++++++++++++++").append(new Date().toString()).append("\n");
        builder.append("----------------晚自习上课：").append("\n");
        for (Entry<String, String> i : wzx.entrySet()) {
            builder.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
        }
        builder.append("----------------调整上课").append("\n");
        for (Entry<String, String> i : appendMap.entrySet()) {
            builder.append(i.getKey()).append(" ").append(i.getValue()).append("\n");
        }
        FileProcessor.append(outFile.toPath(), builder.toString());
        toWookbook.write(new FileOutputStream(toFile));
    }
}