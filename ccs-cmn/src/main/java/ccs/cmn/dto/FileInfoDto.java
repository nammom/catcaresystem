package ccs.cmn.dto;

import lombok.Data;

@Data
public class FileInfoDto {
	/*���� �׷� ���̵�*/
	private String fileGrpId;
	/*���� ���̵�*/
	private String fileId;
	/*���� ����*/
	private int sortOrdr;
	/*���� ���ϸ�*/
	private String orgnFileNm;
	/*���ϸ�*/
	private String fileNm;
	/*Ȯ����*/
	private String fileExtsn;
	/*���� ���*/
	private String filePath;
	/*���� ������*/
	private String fileMg;
	/*��뿩��*/
	private String useAt;
}
