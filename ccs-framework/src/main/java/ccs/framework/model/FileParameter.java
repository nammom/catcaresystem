package ccs.framework.model;


import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ccs.framework.dataservice.BeanService;
import ccs.framework.util.SessionUtility;
import ccs.framework.util.StringUtility;


public class FileParameter {
	
	private static final Logger logger = LogManager.getLogger(FileParameter.class);

	private List<FileInfoVO> files;
	
	
	public FileParameter(){
		this(null);
	}
	
	public FileParameter(List<FileInfoVO> files){
		this.setFiles(files);
	}

	public List<FileInfoVO> getFiles() {
		return files;
	}

	public void setFiles(List<FileInfoVO> files) {
		if(files == null){
			this.files = new ArrayList<FileInfoVO>();
		} else{
			this.files = files;
		}
	}
	
	public static FileParameter create(final HttpServletRequest request) throws Exception{
		FileParameter fileParameter = new FileParameter();
		if(request instanceof MultipartHttpServletRequest){
			Properties config = BeanService.getBeanService("config", Properties.class);
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			logger.debug("FileParameter create" );
			
			//폴더 생성
			String sBaseUploadPath = config.getProperty("Globals.file.Upload");
			String sFilePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM", Locale.KOREA));
        	String saveFolderPath = Paths.get(sBaseUploadPath, sFilePath).toString();
        	File saveFolder = new File(saveFolderPath);
        	if(!saveFolder.exists() || saveFolder.isFile()) {
        		saveFolder.mkdirs();
        	}
        	
        	//파일 생성
        	Iterator<String> fileNames = (Iterator<String>) multiRequest.getFileNames();
        	while(fileNames.hasNext()) {
        		String fileName = fileNames.next();
        		List<MultipartFile> fileList = multiRequest.getFiles(fileName);
        		
        		logger.debug("filename = " + fileName);
        		
        		for(MultipartFile file : fileList) {
        			String orginFileName = file.getOriginalFilename();
        			logger.debug("orginFileName = " + orginFileName);
        			
        			if(StringUtils.isEmpty(orginFileName) || 
        					(StringUtility.isEqual("___AJAX_DATA___", fileName) && StringUtility.isEqual("blob", orginFileName))) {
        				continue;
        			}
        			
        			String realName = FilenameUtils.getBaseName(orginFileName);
        			realName = StringUtility.getStringReplace(realName);
        			String fileExt = FilenameUtils.getExtension(orginFileName);
        			String saveFileName = UUID.randomUUID().toString() + realName + "." + fileExt;
        			long _size = file.getSize();
				    
        			String sRealfilePath = Paths.get(saveFolderPath, saveFileName).toString();
				    file.transferTo(new File(sRealfilePath));
				    
				    String saveFilePath = Paths.get(sFilePath, saveFileName).toString();
				    logger.debug("sRealfilePath = " + sRealfilePath);
				    
				    FileInfoVO fileVO = FileInfoVO.builder()
				    		.orignalFileName(orginFileName)
				    		.saveFileName(saveFileName)
				    		.saveFilePath(saveFilePath)
				    		.uploadSuccess(true)
				    		.fileExtension(fileExt)
				    		.fileInputName(fileName)
				    		.fileSize(_size)
				    		.build();
				    		
				    fileParameter.getFiles().add(fileVO);
				} //- end for
			} //- end while

        } // end if
		
		return fileParameter;
	}
}