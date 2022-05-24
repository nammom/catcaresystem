package ccs.cmn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.util.DownloadUtility;

@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService{
	private static final Logger logger = LogManager.getLogger(UploadFileServiceImpl.class);

	@Resource(name="fileService")
	private FileService fileService;

	@Override
	public List<Map<String, Object>> getFiles(Long FILE_GRUP, String downloadUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Long uploadFiles(Map<String, Object> systemParameter, Long FILE_GRUP, List<FileInfoVO> files, List<Map<String,Object>> filesToDelete) throws Exception {
		
		List<Map<String, Object>> filesToInsert = new ArrayList<>();
		
		for(FileInfoVO file : files) {
			
        			Map<String,Object> fileMap = new HashMap<>();
        			
        			fileMap.put("orgnFileNm", file.getOrignalFileName());
        			fileMap.put("fileNm", file.getSaveFileName());
        			fileMap.put("fileExtsn", file.getFileExtension());
        			fileMap.put("filePath", file.getSaveFilePath());
        			fileMap.put("fileMg", file.getFileSize());
        			fileMap.put("useAt", "Y");
        			fileMap.putAll(systemParameter);
        			
        			filesToInsert.add(fileMap);
        	
		}
		
		//DB¿˙¿Â
		fileService.save(FILE_GRUP, systemParameter, filesToInsert, filesToDelete);
		return FILE_GRUP;
	}

	@Override
	public void downloadFile(Long fileId, HttpServletResponse response) throws Exception {
		Map<String,Object> file = fileService.selectFile(fileId);
		DownloadUtility.create(response).writeBodyFromFile((String)file.get("file_path"))
		.setFileName((String)file.get("orgn_file_nm"))
		.setFileExtension((String)file.get("file_extsn"))
		.startDownload();
	}

}
