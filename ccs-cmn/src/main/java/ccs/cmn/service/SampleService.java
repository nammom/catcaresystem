package ccs.cmn.service;

import java.util.List;
import java.util.Map;

public interface SampleService {

	public List<Map<String, Object>> selectSampleSql();

	public List<Map<String, Object>> selectDatatables(Map<String, Object> param);
}
