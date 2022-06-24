package ccs.cmn.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import ccs.cmn.mapper.FileMapper;
import ccs.cmn.service.FileService;
import ccs.framework.util.HashMapUtility;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	
	@Resource (name="variables")
	private Properties variables;
	
	@Override
	public List<Map<String, Object>> selectFiles(Long FILE_GRUP) throws Exception {
		List<Map<String, Object>> files = fileMapper.selectFilesAllForGrup(FILE_GRUP);
		if(!CollectionUtils.isEmpty(files)) {
			List<Map<String, Object>> result = new ArrayList<>();
			String url = variables.getProperty("Url.Relay") + "/downloadfile";
			
			files.stream().forEach(x ->{
			Map<String, Object> map = new HashMap<>();
			map.put("FILEID", x.get("file_id"));
			map.put("FILENAME", x.get("orgn_file_nm"));
			map.put("URL", url + "?fileId=" + String.valueOf(x.get("file_id")));
			map.put("EXTCLASS", x.get("file_extsn"));
			map.put("FILESIZE", x.get("file_mg"));
			result.add(map);
			});
			
			return result;
		}
		return null;
	}

	@Override
	public Map<String, Object> selectFile(Long FILE_ID) throws Exception {
		List<Map<String, Object>> files = fileMapper.selectFile(FILE_ID);
		return CollectionUtils.isEmpty(files) ? null : files.get(0);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long save(Long FILE_GRUP, Map<String, Object> systemParameter, List<Map<String, Object>> filesToInsert,
			List<Map<String, Object>> filesToDelete) throws Exception {
		//颇老 昏力
		if(!CollectionUtils.isEmpty(filesToDelete)) {
			filesToDelete.stream().forEach(x->fileMapper.deleteFile(x));
		}
		//颇老 历厘
		if(!CollectionUtils.isEmpty(filesToInsert)) {
			Map<String, Object> param = HashMapUtility.<String,Object>create().add(systemParameter).toMap();
			if(ObjectUtils.isEmpty(FILE_GRUP)) {
				fileMapper.insertFileGroup(param);
			}
			final Long fileGrupId = ObjectUtils.isEmpty(FILE_GRUP)? (Long) param.get("fileGrpId") : FILE_GRUP;
			filesToInsert.stream().forEach(x->{
												x.putAll(systemParameter);
												x.put("fileGrpId", fileGrupId);
												fileMapper.insertFile(x);
											});
		}
		
		//return File_group
		return FILE_GRUP;
	}


}
