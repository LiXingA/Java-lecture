package student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.jsonldjava.shaded.com.google.common.base.Objects;

import student.excel.WkRecord;

public class Details {

	private Object[][] data;
	private Map<String, WkRecord> wkMap;
	private Map<String, List<Record>> zyMap;
	private Object[][] firstData;

	public Details(Object[][] data, Map<String, WkRecord> wkMapByName, Map<String, List<Record>> studentRecordByName) {
		super();
		this.data = data;
		if (ConfigData.DEFAULT_FIRST) {
			List<Object[]> objs = new ArrayList<>();
			int indexOf = Arrays.asList(Record.columnIdentifiers).indexOf(Record.FIRST);
			for (Object[] objects : this.data) {
				if ((boolean) objects[indexOf]) {
					objs.add(objects);
				}
			}
			this.firstData = objs.toArray(new Object[objs.size()][]);
		} else {
			this.firstData = data;
		}
		this.wkMap = wkMapByName;
		this.zyMap = studentRecordByName;

	}

	public Object[][] getData() {
		return data;
	}

	public Map<String, WkRecord> getWkMap() {
		return wkMap;
	}

	public Map<String, List<Record>> getZyMap() {
		return zyMap;
	}

	public Object[][] getFirstData() {
		return firstData;
	}

	public void setFirstData(Object[][] data2) {
		this.firstData = data2;
	}

	public void print(String name) {
		WkRecord wkRecord = wkMap.get(name);
		if (wkRecord != null) {
			System.err.println(wkRecord.toString());
		}
		List<Record> rList = zyMap.get(name);
		for (Record record : rList) {
			if (record.isFirst()) {
				System.err.println(record.toString());
			}
		}
	}

}
