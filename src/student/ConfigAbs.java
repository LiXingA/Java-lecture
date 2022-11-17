package student;

public interface ConfigAbs {
	Details getGenDatas();

	ClazzConfig getClazzConfig();

	void setClazzConfig(ClazzConfig config) throws Exception;
}
