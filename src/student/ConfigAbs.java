package student;

public interface ConfigAbs {
	Details getGenDatas();

	ClazzConfig getClazzConfig();

	void setClazzConfig(ClazzConfig config) throws Exception;

	void setClazzConfigs(ClazzConfig[] clazzConfigs);
}
