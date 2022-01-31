package ccs.framework.model;


import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Properties;

import ccs.framework.dataservice.BeanService;


public class FileInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3112511643876509617L;

	private String orignalFileName;
	private String saveFileName;
	private String fileExtension;
	private long fileSize;
	private Boolean uploadSuccess;
	private String saveFilePath;
	private String fileInputName;
	
	public String getFileInputName() {
		return fileInputName;
	}

	public void setFileInputName(String fileInputName) {
		this.fileInputName = fileInputName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public FileInfoVO(){
		this.uploadSuccess = false;
	}
	
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Boolean getUploadSuccess() {
		return uploadSuccess;
	}
	public void setUploadSuccess(Boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getOrignalFileName() {
		return orignalFileName;
	}

	public void setOrignalFileName(String orignalFileName) {
		this.orignalFileName = orignalFileName;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}
	
	public String getFileFullPath(){
		Properties config = BeanService.getBeanService("config", Properties.class);
		String sBaseUpladPath = config.getProperty("Globals.file.Upload");
		return Paths.get(sBaseUpladPath,this.saveFilePath,this.saveFileName).toString();
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
}
