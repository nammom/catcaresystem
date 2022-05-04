package ccs.cmn.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import ccs.cmn.mapper.FileMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.util.HashMapUtility;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	
	FileMapper fileMapper;
	
	@Override
	public List<Map<String, Object>> selectFiles(String FILE_GRUP) throws Exception {
		return fileMapper.selectFilesAllForGrup(FILE_GRUP).orElse(null);
	}

	@Override
	public Map<String, Object> selectFile(String FILE_ID) throws Exception {
		Optional<Map<String, Object>> files = fileMapper.selectFile(FILE_ID);
		return files.orElseGet(null);
	}

	@Override
	public String save(String FILE_GRUP, Map<String, Object> systemParameter, List<Map<String, Object>> filesToInsert,
			List<Map<String, Object>> filesToDelete) throws Exception {
		//颇老 昏力
		filesToDelete.stream().forEach(x->fileMapper.deleteFile(x));
	
		//颇老 历厘
		Map<String, Object> param = HashMapUtility.<String,Object>create().add(systemParameter).toMap();
		if(StringUtils.isEmpty(FILE_GRUP)) {
			fileMapper.insertFileGroup(param);
		}
		final String fileGrupId = FILE_GRUP;
		filesToInsert.stream().forEach(x->{
											x.putAll(systemParameter);
											x.putAll(param);
											fileMapper.insertFile(x);
										});
		
		//return File_group
		return FILE_GRUP;
	}


}
