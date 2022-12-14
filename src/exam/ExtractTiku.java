package exam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTiku {
    public static void main(String[] args) throws IOException {
        Pattern p = Pattern.compile("^(\\d+)\\(([ABCD])\\)(.*$)", Pattern.CASE_INSENSITIVE);
        System.out.println("2720(A)       ______是一种价格低廉、易于连接的有线传输介质。".matches("^\\d"));
        System.out.println(p.matcher("2720(A)       ______是一种价格低廉、易于连接的有线传输介质。").find());
        File file = new File("./timu.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        List<TiMu> list = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            Matcher matcher = p.matcher(line);
            if (matcher.find()) {// "^(\\d+)(\\([ABCD]\\))")) {
                String num = matcher.group(1);
                String daan = matcher.group(2);
                String ti = matcher.group(3);
                String A = "", B = "", C = "", D = "", tmp;
                for (int i = 0; i < 10; i++) {
                    tmp = bufferedReader.readLine();
                    if (tmp.startsWith("A.")) {
                        A = tmp;
                    } else if (tmp.startsWith("B.")) {
                        B = tmp;
                    } else if (tmp.startsWith("C.")) {
                        C = tmp;
                    } else if (tmp.startsWith("D.")) {
                        D = tmp;
                        list.add(new TiMu(num, daan, ti, A, B, C, D));
                        break;
                    }
                }
            }
        }
        System.out.println(list.size());
        bufferedReader.close();
        File outFile = new File("./outFile.txt");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (TiMu tiMu : list) {
            bufferedWriter.append(tiMu.tranlate()).append("\n");
        }
        bufferedWriter.close();
        // suiji(list);
        excel(list);
    }

    static void excel(List<TiMu> list) throws IOException {
        File examFile = new File("./excel.txt");
        if (!examFile.exists()) {
            examFile.createNewFile();
        }
        BufferedWriter examBufferedWriter = new BufferedWriter(new FileWriter(examFile));
        Set<TiMu> set = new TreeSet<>();
        for (TiMu tiMu : list) {
            if (Pattern.compile("excel", Pattern.CASE_INSENSITIVE).matcher(tiMu.tranlate()).find()) {
                set.add(tiMu);
            }
        }
        for (TiMu tiMu : set) {
            examBufferedWriter.append(tiMu.tranlate().replace("excel 2003", "excel 2016")).append("\n");
        }
        examBufferedWriter.close();
    }

    static void suiji(List<TiMu> list) throws IOException {
        File examFile = new File("./examFile2.txt");
        if (!examFile.exists()) {
            examFile.createNewFile();
        }
        BufferedWriter examBufferedWriter = new BufferedWriter(new FileWriter(examFile));
        Set<TiMu> set = new TreeSet<>();
        while (set.size() < 80) {
            set.add(list.get((int) (1000 * Math.random())));
        }
        for (TiMu tiMu : set) {
            examBufferedWriter.append(tiMu.tranlate()).append("\n");
        }
        examBufferedWriter.close();
    }

    static class PanDuanTiMu implements Comparable<PanDuanTiMu> {
        private int num;
        private String daan;
        private String ti;

        public PanDuanTiMu(String num, String daan, String ti) {
            this.num = Integer.parseInt(num);
            this.daan = daan;
            this.ti = ti.trim();
        }

        String tranlate() {
            return this.tranlate(false, false);
        }

        String tranlate(boolean isNum, boolean hide) {
            StringBuilder builder = new StringBuilder(isNum ? num + "．" : "").append("【判断题】");
            builder.append(ti).append("\n");
            builder.append("答案：").append(!hide ? daan : "你猜").append("\n");
            return builder.toString();

        }

        @Override
        public int compareTo(PanDuanTiMu o) {
            return o.num - this.num;
        }

    }

    static class TiMu implements Comparable<TiMu> {
        private int num;
        private String daan;
        private String ti;
        private String A, B, C, D;

        public TiMu(String num, String daan, String ti, String a, String b, String c, String d) {
            this.num = Integer.parseInt(num);
            this.daan = daan;
            this.ti = ti.trim();
            A = a;
            B = b;
            C = c;
            D = d;
        }

        String tranlate() {
            return this.tranlate(false, false);
        }

        String tranlate(boolean isNum, boolean hide) {
            StringBuilder builder = new StringBuilder(isNum ? num + "．" : "").append("【单选题】");
            builder.append(ti).append("\n");
            builder.append(A).append("\n");
            builder.append(B).append("\n");
            builder.append(C).append("\n");
            builder.append(D).append("\n");
            builder.append("答案：").append(!hide ? daan : "你猜").append("\n");
            builder.append("难易程度：中").append("\n");
            builder.append("答案解析：无").append("\n");
            builder.append("知识点：").append("\n");
            return builder.toString();

        }

        @Override
        public int compareTo(TiMu o) {
            return o.num - this.num;
        }

    }
}
