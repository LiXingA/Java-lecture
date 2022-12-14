package student.excel;

import java.util.Date;

public class ChatInfo {
    private Date time;
    private String info;

    public ChatInfo(Date dateFirstLine, String info) {
        super();
        this.time = dateFirstLine;
        this.info = info;
    }

    public Date getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", WkCalc.simpleDateFormatXie.format(time), info);
    }

}
