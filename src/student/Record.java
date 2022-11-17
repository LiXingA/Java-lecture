package student;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;

public class Record {
	private final Types types;
	private final String path;
	private final String name;
	private int times = 0;

	private String weekIndex = "";
	private String repeatTimes = "";
	private int wChecks = 0;
	private int wChatTimes = 0;
	private boolean first;
	private final long createTime;

	public Record(Types types, String path, String name, long l) {
		super();
		this.types = types;
		this.path = path;
		this.name = name;
		this.createTime = l;
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
		return wChecks;
	}

	public int getwChatTimes() {
		return wChatTimes;
	}

	protected static final Object FIRST = "首选";
	static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
	static final String TYPE = "文件类型";
	static final String NAME = "学生名字";

	public static final Object[] columnIdentifiers = new Object[] { NAME,

			"作业路径", TYPE, "总次数",

			"周次", "重复提交",

			"网课签到", "网课活跃度",

			FIRST, "综合评分",

			"创建时间" };

	public Object[] toDatas() {
		return new Object[] { this.name,

				this.path, this.types, this.times,

				this.weekIndex, this.repeatTimes,

				this.wChecks, this.wChatTimes,

				this.first, this.getScore(),

				simpleDateFormat.format(new Date(this.createTime)) };
	}

	@Override
	public String toString() {
		if (this.first) {
			return String.format("%s=>上交作业次数：%s ,网课签到次数： %s ,网课活跃度： %s ,综合评分：%s", this.name, this.times, this.wChecks,
					this.wChatTimes, this.getScore());
		} else {
			return super.toString();
		}
	}

	private long getScore() {
		return Math.round((this.times * ConfigData.ZY_RATE + this.wChecks * ConfigData.CHECK_RATE
				+ this.wChatTimes * ConfigData.CHAT_RATE) * 100.0 /

				(ConfigData.MAX_ZY_TIMES * ConfigData.ZY_RATE + ConfigData.MAX_CHECK_TIMES * ConfigData.CHECK_RATE
						+ ConfigData.MAX_CHAT_TIMES * ConfigData.CHAT_RATE));
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

	public void setwChecks(int wChecks) {
		this.wChecks = wChecks;
	}

	public void setwChatTimes(int wChatTimes) {
		this.wChatTimes = wChatTimes;
	}

	public void setFirst(boolean b) {
		this.first = b;
	}

	public boolean isFirst() {
		return first;
	}

}