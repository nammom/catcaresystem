package ccs.framework.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileInfoVO {
	private String orignalFileName;
	private String saveFileName;
	private String fileExtension;
	private long fileSize;
	private Boolean uploadSuccess;
	private String saveFilePath;
	private String fileInputName;
	
	@Builder
	public FileInfoVO(String orignalFileName, String saveFileName, String fileExtension, long fileSize,
			Boolean uploadSuccess, String saveFilePath, String fileInputName) {
		this.orignalFileName = orignalFileName;
		this.saveFileName = saveFileName;
		this.fileExtension = fileExtension;
		this.fileSize = fileSize;
		this.uploadSuccess = uploadSuccess;
		this.saveFilePath = saveFilePath;
		this.fileInputName = fileInputName;
	}
	
	
}
