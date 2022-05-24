package ccs.cmn.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ccs.framework.model.FileInfoVO;


public interface UploadFileService {
	List<Map<String,Object>> getFiles(Long FILE_GRUP, String downloadUrl) throws Exception;
	
	Long uploadFiles(Map<String, Object> systemParameter, Long FILE_GRUP, List<FileInfoVO> files, List<Map<String,Object>> filesToDelete) throws Exception;
	
	//String uploadFiles2(String FILE_GRUP, FileInfoDto uploadedFiles, List<Map<String,Object>> filesToDelete, Map<String,Object> sysParameter) throws Exception;
	
	void downloadFile(Long fileId, HttpServletResponse response) throws Exception;
}
