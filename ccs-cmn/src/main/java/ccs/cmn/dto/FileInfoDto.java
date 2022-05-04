package ccs.cmn.dto;

import lombok.Data;

@Data
public class FileInfoDto {
	/*파일 그룹 아이디*/
	private String fileGrpId;
	/*파일 아이디*/
	private String fileId;
	/*파일 순번*/
	private int sortOrdr;
	/*원본 파일명*/
	private String orgnFileNm;
	/*파일명*/
	private String fileNm;
	/*확장자*/
	private String fileExtsn;
	/*파일 경로*/
	private String filePath;
	/*파일 사이즈*/
	private String fileMg;
	/*사용여부*/
	private String useAt;
}
