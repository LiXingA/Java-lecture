package student;

import java.io.File;
import java.util.List;

public class ClazzConfig {

	private String name;
	private String lecture;
	private String zuoyePath;
	private String wkPath;
	private List<String> names;

	public ClazzConfig(String name, String lecture, String zuoyePath, String wkPath, List<String> names) {
		super();
		this.name = name;
		this.lecture = lecture;
		this.zuoyePath = new File(zuoyePath).getAbsolutePath();
		this.wkPath = wkPath;
		this.names = names;
	}

	public String getName() {
		return name;
	}

	public String getLecture() {
		return lecture;
	}

	public String getZuoyePath() {
		return zuoyePath;
	}

	public String getWkPath() {
		return wkPath;
	}

	public static String getClazzsPath() {
		return ".\\clazzs.xlsx";
	}

	public List<String> getNames() {
		return names;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.name, this.lecture);
	}

}
