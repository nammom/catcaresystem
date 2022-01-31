package ccs.framework.model;


import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ccs.framework.dataservice.BeanService;
import ccs.framework.dataservice.IdGnrService;
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
        	IdGnrService UUIDService  = BeanService.getBeanService("UUIDService", IdGnrService.class);
        	
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        	if(logger.isDebugEnabled()){
				logger.debug("FileParameter create" );
			}
        	
        	String sBaseUpladPath = config.getProperty("Globals.file.Upload");
			String sFilePath = new SimpleDateFormat("yyyy/MM", Locale.KOREA).format(new Date());
			String storePathString = Paths.get(sBaseUpladPath,sFilePath).toString();
			File saveFolder = new File(storePathString);
	    	if (!saveFolder.exists() || saveFolder.isFile()) {
			    saveFolder.mkdirs();
			}
	    	
			Iterator<String> fileNames = (Iterator<String>) multiRequest.getFileNames();
			while(fileNames.hasNext()){
				String fileName = fileNames.next();
				List<MultipartFile> filelist = multiRequest.getFiles(fileName);
				
				
				if(logger.isDebugEnabled()){
					logger.debug("fileName = " + fileName);
				}
				
				for(MultipartFile file : filelist){
					FileInfoVO fileVO = new FileInfoVO();
		 		    String orginFileName = file.getOriginalFilename();
		 		    
		 		    
		 		   if(logger.isDebugEnabled()){
		 			  logger.debug("orginFileName = " + orginFileName);
					}
		 		   
		 		 //--------------------------------------
				    // �썝 �뙆�씪紐낆씠 �뾾�뒗 寃쎌슦 泥섎━
				    // (泥⑤�媛� �릺吏� �븡�� input file type)
				    //--------------------------------------
				    if ("".equals(orginFileName)) {
				    	continue;
				    }
				    
				    int index = orginFileName.lastIndexOf(".");
					String realName = orginFileName.substring(0, index);
					realName = StringUtility.getStringReplace(realName); // 怨듬갚, �듅�닔臾몄옄 �젣嫄�
				    String fileExt = FilenameUtils.getExtension(orginFileName); // �솗�옣�옄
				    
				    String saveFileName = (String)UUIDService.getId();
				    fileVO.setSaveFileName(saveFileName);
				    long _size = file.getSize();
				    fileVO.setFileSize(_size);
				    
				    // �뙆�씪 �깮�꽦
				    String sRealfilePath = Paths.get(storePathString ,saveFileName).toString();
				    file.transferTo(new File(sRealfilePath));


			    	fileVO.setSaveFileName(saveFileName);
			    	fileVO.setSaveFilePath(sFilePath);
			    	fileVO.setUploadSuccess(true);
			    	fileVO.setFileExtension(fileExt);
			    	fileVO.setFileInputName(fileName);
			    	if(StringUtility.isNullOrBlank(fileExt)){
			    		fileVO.setOrignalFileName(realName);
			    	} else{
			    		fileVO.setOrignalFileName(realName + "." + fileExt);
			    	}
			    	
				    fileParameter.getFiles().add(fileVO);
				} //- end for
			} //- end while

        } // end if
		
		return fileParameter;
	}
}