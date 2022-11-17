package student.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WkRecord {

	private final List<Date> checks = new ArrayList<>();
	private final List<ChatInfo> chats = new ArrayList<>();

	public WkRecord() {
	}

	public List<Date> getChecks() {
		return checks;
	}

	public List<ChatInfo> getChats() {
		return chats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (ChatInfo chatInfo : chats) {
			builder.append(chatInfo.toString()).append("\n");
		}

		for (Date check : checks) {
			builder.append(WkCalc.simpleDateFormat.format(check)).append("\n");
		}

		return builder.toString();
	}

}

class ChatInfo {
	private Date time;
	private String info;

	public ChatInfo(Date dateFirstLine, String info) throws ParseException {
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