package ccs.cmn.service.impl;

import java.io.File;
import java.nio.file.Paths;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.dataservice.BeanService;
import ccs.framework.util.SessionUtility;
import ccs.framework.util.StringUtility;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService{
	private static final Logger logger = LogManager.getLogger(UploadFileServiceImpl.class);

	FileService fileService;

	@Override
	public List<Map<String, Object>> getFiles(String FILE_GRUP, String downloadUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadFiles(HttpServletRequest request, Map<String, Object> systemParameter, String FILE_GRUP, List<Map<String,Object>> filesToDelete) throws Exception {
		
		List<Map<String, Object>> filesToInsert = new ArrayList<>();
		
		if(request instanceof MultipartHttpServletRequest) {
			Properties config = BeanService.getBeanService("config", Properties.class);
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			UserDetails userInfo = SessionUtility.getUserDetails();
			logger.debug("FileParameter create" );
			
			//弃歹 积己
			String sBaseUploadPath = config.getProperty("Globals.file.Upload");
			String sFilePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM", Locale.KOREA));
        	String saveFolderPath = Paths.get(sBaseUploadPath, sFilePath).toString();
        	File saveFolder = new File(saveFolderPath);
        	if(!saveFolder.exists() || saveFolder.isFile()) {
        		saveFolder.mkdirs();
        	}
        	
        	//颇老 积己
        	Iterator<String> fileNames = (Iterator<String>) multiRequest.getFileNames();
        	while(fileNames.hasNext()) {
        		String fileName = fileNames.next();
        		List<MultipartFile> fileList = multiRequest.getFiles(fileName);
        		
        		logger.debug("filename = " + fileName);
        		
        		for(MultipartFile file : fileList) {
        			String orginFileName = file.getOriginalFilename();
        			logger.debug("orginFileName = " + orginFileName);
        			
        			if(StringUtils.isEmpty(orginFileName)) {
        				continue;
        			}
        			
        			String realName = FilenameUtils.getBaseName(orginFileName);
        			realName = StringUtility.getStringReplace(realName);
        			String fileExt = FilenameUtils.getExtension(orginFileName);
        			String saveFileName = UUID.randomUUID().toString() + realName;
        			long _size = file.getSize();
        			
        			String sRealfilePath = Paths.get(saveFolderPath, saveFileName).toString();
        			file.transferTo(new File(sRealfilePath));
        			logger.debug("sRealfilePath = " + sRealfilePath);
        			
        			Map<String,Object> fileMap = new HashMap<>();
        			fileMap.put("orgnFileNm", orginFileName);
        			fileMap.put("fileNm", saveFileName);
        			fileMap.put("fileExtsn", fileExt);
        			fileMap.put("filePath", sRealfilePath);
        			fileMap.put("fileMg", _size);
        			fileMap.put("useAt", "Y");
        			fileMap.put("userId", userInfo.getUsername());
        			filesToInsert.add(fileMap);
        		}
        		
        	}
        	
		}
		
		//DB历厘
		fileService.save(FILE_GRUP, systemParameter, filesToInsert, filesToDelete);
		return null;
	}

	@Override
	public void downloadFile(String fileId, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
