package ccs.cmn.service;

import java.util.List;
import java.util.Map;

public interface FileService {
	public List<Map<String, Object>> selectFiles(Long FILE_GRUP) throws Exception;

	public Map<String, Object> selectFile(Long FILE_ID) throws Exception;

	public Long save(Long FILE_GRUP, Map<String, Object> systemParameter, List<Map<String, Object>> filesToInsert,
			List<Map<String, Object>> filesToDelete) throws Exception;
}
