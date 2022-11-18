package student;

import java.io.File;
import java.util.List;

import student.excel.WkCalc;

public class ClazzConfig {

	private String name;
	private String lecture;
	private String zuoyePath;
	private String wkPath;
	private List<String> names;
	private String errNameReg;

	public ClazzConfig(String name, String lecture, String zuoyePath, String wkPath, List<String> names,
			String errNameReg) {
		super();
		this.name = name;
		this.lecture = lecture;
		this.zuoyePath = new File(zuoyePath).getAbsolutePath();
		this.wkPath = wkPath;
		this.names = names;
		System.out.println(errNameReg);
		String oldReg = errNameReg;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < oldReg.length(); i++) {
			String cur = oldReg.substring(i, i + 1);
			if (WkCalc.isHan(cur)) {
				builder.append("\\u").append(WkCalc.charToHex(cur.charAt(0)));
			} else {
				builder.append(cur.charAt(0));
			}
		}
		System.out.println(builder);
		this.errNameReg = errNameReg;
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

	public String getErrNameReg() {
		return errNameReg;
	}

	public void setErrNameReg(String errNameReg) {
		this.errNameReg = errNameReg;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.name, this.lecture);
	}

}
