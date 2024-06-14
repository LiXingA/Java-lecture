package exam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import exam.ExtractTiku.PanDuanTiMu;
import exam.ExtractTiku.TiMu;
import student.HandleFileI;
import student.excel.FileProcessor;

public class ExtractJavaTiku {

    public static void main(String[] args) throws IOException {
        List<TiMu> list = new ArrayList<>();
        List<PanDuanTiMu> pdlist = new ArrayList<>();
        HandleFileI handler = new HandleFileI() {

            @Override
            public void handleFile(File file) throws Exception {
                if (file.getName().endsWith(".html")) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("选择题")) {
                            bufferedReader.readLine();
                            String ti = bufferedReader.readLine().replace("&nbsp;", " ").replace("<br>", "\n")
                                    .replaceFirst("<p>$", "").trim();
                            System.out.println(ti);
                            bufferedReader.readLine();
                            String a = bufferedReader.readLine().replace("&nbsp;", " ").replace("&lt;", "<")
                                    .replace("&gt;", ">").replace("<br>", "\n")
                                    .replace(
                                            "<input type=\"radio\" name=\"r\" onClick=\"ff(0)\"/><a href=\"#\" onclick=\"ff(0)\">",
                                            "")
                                    .replaceFirst("</a><p>$", "").trim();
                            bufferedReader.readLine();
                            String b = bufferedReader.readLine().replace("&nbsp;", " ").replace("&lt;", "<")
                                    .replace("&gt;", ">").replace("<br>", "\n")
                                    .replace(
                                            "<input type=\"radio\" name=\"r\" onClick=\"ff(1)\"/><a href=\"#\" onclick=\"ff(1)\">",
                                            "")
                                    .replaceFirst("</a><p>$", "").trim();
                            bufferedReader.readLine();
                            String c = bufferedReader.readLine().replace("&nbsp;", " ").replace("&lt;", "<")
                                    .replace("&gt;", ">").replace("<br>", "\n")
                                    .replace(
                                            "<input type=\"radio\" name=\"r\" onClick=\"ff(2)\"/><a href=\"#\" onclick=\"ff(2)\">",
                                            "")
                                    .replaceFirst("</a><p>$", "").trim();
                            bufferedReader.readLine();
                            String d = bufferedReader.readLine();
                            d = d.contains(
                                    "<input type=\"radio\" name=\"r\" onClick=\"ff(3)\"/><a href=\"#\" onclick=\"ff(3)\">")
                                            ? d.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">")
                                                    .replace("<br>", "\n")
                                                    .replace(
                                                            "<input type=\"radio\" name=\"r\" onClick=\"ff(3)\"/><a href=\"#\" onclick=\"ff(3)\">",
                                                            "")
                                                    .replaceFirst("</a><p>$", "").trim()
                                            : "D．其他答案";
                            list.add(new TiMu(file.getName().replace(".html", ""), "未知", ti, a, b, c, d));
                            break;
                        } else if (line.contains("判断题")) {
                            bufferedReader.readLine();
                            String ti = bufferedReader.readLine().replace("&nbsp;", " ").replace("<br>", "\n")
                                    .replaceFirst("<p>$", "").trim();
                            System.out.println(ti);
                            pdlist.add(new PanDuanTiMu(file.getName().replace(".html", ""), "未知", ti));
                            break;
                        }
                    }
                    bufferedReader.close();
                }
            }

            @Override
            public void handleDir(File directory) throws Exception {
                // TODO Auto-generated method stub

            }
        };

        HandleFileI.Tools.scanDirRecursion(new File("./resource/javaexam/"), handler);
        File outFile = new File("./javaTiku.txt");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (TiMu tiMu : list) {
            bufferedWriter.append(tiMu.tranlate()).append("\n");
        }
        for (PanDuanTiMu tiMu : pdlist) {
            bufferedWriter.append(tiMu.tranlate()).append("\n");
        }
        bufferedWriter.close();
    }
}
