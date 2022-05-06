package ccs.cmn.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.dataservice.BeanService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.util.SessionUtility;
import ccs.framework.util.StringUtility;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService{
	private static final Logger logger = LogManager.getLogger(UploadFileServiceImpl.class);

	private final FileService fileService;

	@Override
	public List<Map<String, Object>> getFiles(String FILE_GRUP, String downloadUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public String uploadFiles(Map<String, Object> systemParameter, String FILE_GRUP, List<FileInfoVO> files, List<Map<String,Object>> filesToDelete) throws Exception {
		
		List<Map<String, Object>> filesToInsert = new ArrayList<>();
		
		for(FileInfoVO file : files) {
			
        			Map<String,Object> fileMap = new HashMap<>();
        			
        			fileMap.put("orgnFileNm", file.getOrignalFileName());
        			fileMap.put("fileNm", file.getFileInputName());
        			fileMap.put("fileExtsn", file.getFileExtension());
        			fileMap.put("filePath", file.getSaveFilePath());
        			fileMap.put("fileMg", file.getFileSize());
        			fileMap.put("useAt", "Y");
        			fileMap.putAll(systemParameter);
        			
        			filesToInsert.add(fileMap);
        	
		}
		
		//DB¿˙¿Â
		fileService.save(FILE_GRUP, systemParameter, filesToInsert, filesToDelete);
		return null;
	}

	@Override
	public void downloadFile(String fileId, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
