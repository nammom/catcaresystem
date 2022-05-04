package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ccs.framework.annotations.Mapper;
import ccs.framework.util.HashMapUtility;

@Mapper(value="FileMapper")
public interface FileMapper {

	public Optional<List<Map<String, Object>>> selectFilesAllForGrup(String FILE_GRUP);

	public Optional<Map<String, Object>> selectFile(String FILE_ID);

	public void deleteFile(Map<String, Object> param);

	public void insertFileGroup(Map<String, Object> param);

	public void insertFile(Map<String, Object> param);

}
