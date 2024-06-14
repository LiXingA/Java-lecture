package student;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import student.excel.ChatInfo;

public class Record {
	private final Types types;
	private final String path;
	private final int numIndex;
	private final String name;
	private int times = 0;

	private String weekIndex = "";
	private String repeatTimes = "";
	private boolean first;
	private final long createTime;
	private int grade;
	private List<Date> wangChecks = Collections.EMPTY_LIST;
	private List<ChatInfo> wangChats = Collections.EMPTY_LIST;

	public Record(int numIndex, Types types, String path, String name, long l) {
		super();
		this.numIndex = numIndex;
		this.types = types;
		this.path = path;
		this.name = name;
		this.createTime = l;
	}

	public int getNumIndex() {
		return numIndex;
	}

	public Types getTypes() {
		return types;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getwChecks() {
		return wangChecks.size();
	}

	public int getwChatTimes() {
		return wangChats.size();
	}

	protected static final Object FIRST = "首选";
	static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static final String TYPE = "文件类型";
	static final String NUM_INDEX = "序号";
	static final String NAME = "学生名字";

	public static final Object[] columnIdentifiers = new Object[] { NUM_INDEX, NAME,

			"作业路径", TYPE, "总次数",

			"周次", "重复提交",

			"网课签到", "网课活跃度",

			FIRST, "综合评分",

			"评级",

			"创建时间" };

	public Object[] toDatas() {
		return new Object[] {

				this.numIndex, this.name,

				this.path, this.types, this.times,

				this.weekIndex, this.repeatTimes,

				this.wangChecks.size(), this.wangChats.size(),

				this.first, this.getScore(),

				this.grade,

				simpleDateFormat.format(new Date(this.createTime)) };
	}

	@Override
	public String toString() {
		if (this.first) {
			return String.format("%s=>上交作业次数：%s ,网课签到次数： %s ,网课活跃度： %s ,综合评分：%s ,评级：%s", this.name, this.times,
					this.wangChecks.size(), this.wangChats.size(), this.getScore(), this.grade);
		} else {
			return String.format("路径：%s，评级：%s", this.path, this.grade);
		}
	}

	private long getScore() {
		return (ConfigData.BASE_RATE
				+ Math.round((this.times * ConfigData.ZY_RATE + this.wangChecks.size() * ConfigData.CHECK_RATE
						+ this.wangChats.size() * ConfigData.CHAT_RATE) * (100.0 - ConfigData.BASE_RATE) /

						(ConfigData.MAX_ZY_TIMES * ConfigData.ZY_RATE
								+ ConfigData.MAX_CHECK_TIMES * ConfigData.CHECK_RATE
								+ ConfigData.MAX_CHAT_TIMES * ConfigData.CHAT_RATE)));
	}

	public String getWeekIndex() {
		return weekIndex;
	}

	public void setWeekIndex(String weekIndex) {
		this.weekIndex = weekIndex;
	}

	public void setRepeatTimes(String repeatTimes) {
		this.repeatTimes = repeatTimes;
	}

	public void setFirst(boolean b) {
		this.first = b;
	}

	public boolean isFirst() {
		return first;
	}

	public void setWangChats(List<ChatInfo> chats) {
		this.wangChats = chats;
	}

	public void setWangChecks(List<Date> checks) {
		this.wangChecks = checks;
	}

}