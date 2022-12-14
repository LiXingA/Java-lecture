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

public class ExtractWangluoTiku {

    public static void main(String[] args) throws IOException {
        List<TiMu> list = new ArrayList<>();
        List<PanDuanTiMu> pdlist = new ArrayList<>();
        HandleFileI handler = new HandleFileI() {

            @Override
            public void handleFile(File file) throws Exception {
                if (file.getName().endsWith(".txt")) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (!line.trim().equals("")) {
                            String ti = line.trim();
                            String next = bufferedReader.readLine().trim();
                            if (next.startsWith("A．")) {
                                String a = next;
                                String b = bufferedReader.readLine().trim();
                                String c = bufferedReader.readLine().trim();
                                String d = bufferedReader.readLine().trim();
                                bufferedReader.readLine();
                                bufferedReader.readLine();
                                bufferedReader.readLine();
                                String daan = bufferedReader.readLine().trim();
                                if (daan.equals("")) {
                                    break;
                                }
                                daan = daan.substring(0, 1);
                                list.add(new TiMu("" + list.size(), daan, ti, a, b, c, d));
                            } else if (next.trim().equals("正确")) {
                                bufferedReader.readLine();
                                bufferedReader.readLine();
                                bufferedReader.readLine();
                                bufferedReader.readLine();
                                String daan = bufferedReader.readLine().trim();
                                if (!daan.equals("正确") && !daan.equals("错误")) {
                                    break;
                                }
                                pdlist.add(new PanDuanTiMu("" + pdlist.size(), daan.equals("正确") ? "对" : "错", ti));
                            } else {
                                break;
                            }
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

        HandleFileI.Tools.scanDirRecursion(new File("./resource/wangluo"), handler);
        File outFile = new File("./wangluoTiku2.txt");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (TiMu tiMu : list) {
            bufferedWriter.append(tiMu.tranlate(false, false)).append("\n");
        }
        for (PanDuanTiMu tiMu : pdlist) {
            bufferedWriter.append(tiMu.tranlate(false, false)).append("\n");
        }
        bufferedWriter.close();
    }
}
