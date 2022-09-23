package ccs.cmn.service.impl.care;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import ccs.cmn.mapper.FileMapper;
import ccs.cmn.mapper.care.CharacterMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.care.CharacterService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;


@Service(value="CharacterService")
public class CharacterServiceImpl implements CharacterService {

	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="CharacterMapper")
	private CharacterMapper characterMapper;

	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	
	/**
	 * ����� Ư¡ ���� ��� ��ȸ
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<Map<String, Object>> selectCharacterList(Map<String, Object> param) throws Exception {
		List<Map<String, Object>> characterList = characterMapper.selectCharacterList(param);
		for(Map<String, Object> e : characterList) {
			//÷������ ����
			if(e.get("file_grp_id") != null) {
				Long file_grp_id = (Long)e.get("file_grp_id");
				List<Map<String, Object>> fileList = fileMapper.selectFilesAllForGrup(file_grp_id);
				if(!CollectionUtils.isEmpty(fileList)) {
					e.put("fileList", fileList);
				}
			}
			e.put("_SESSION_USER_CD_", param.get("_SESSION_USER_CD_"));
		}
		return characterList;
	}
	
	/**
	 * ����� Ư¡ �� ��ȸ
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectCharacterDetail(Map<String, Object> param) throws Exception {
		
		//�޿����� ��ȸ
		List<Map<String, Object>> characterList = characterMapper.selectCharacterByCd(param);
		if(!CollectionUtils.isEmpty(characterList)) {
			Map<String, Object> detail = characterList.get(0);
			//÷������ ����
			if(detail.get("file_grp_id") != null) {
				Long file_grp_id = (Long)detail.get("file_grp_id");
				List<Map<String, Object>> fileList = fileService.selectFiles(file_grp_id);
				if(!CollectionUtils.isEmpty(fileList)) {
					detail.put("fileList", fileList);
				}
			}
			return detail;
		}else {
			return null;
		}
	}

	/**
	 * Ư¡ ���� ����(����)
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertCharacter(Map<String, Object> param, FileParameter fileParameter) throws Exception{
		//----------------------------------------------------------- ���� ���� ������Ʈ ---------------------------------------------
		
		// ���ε��� �������� 
		List<FileInfoVO> uploadedFiles = fileParameter.getFiles();
		// ������ �־����� ������ ���� ���� ���
		List<Map<String,Object>> filesToDelete = (List<Map<String,Object>>)param.get("deleteFiles");
		
		// ������ ���� �׷������� ������ ������Ʈ�� ���ؼ� �Ķ���ͷ� ���� ������ ����
		Long FILE_GRP_ID = null;
		if(StringUtils.isNotEmpty((String)param.get("file_grp_id"))) {
			FILE_GRP_ID = Long.parseLong((String)param.get("file_grp_id"));
		}
		FILE_GRP_ID = uploadFileService.uploadFiles(param, FILE_GRP_ID, uploadedFiles, filesToDelete);
		param.put("file_grp_id", FILE_GRP_ID);
	
		//----------------------------------------------------------- ���� ���� ������Ʈ ---------------------------------------------

		if(ObjectUtils.isEmpty(param.get("character_cd"))){
			characterMapper.insertCharacter(param);
		}else {
			if(this.checkUserCd(param)) {
				characterMapper.updateCharacter(param);
			}else {
				throw new Exception();
			}
		}
	}

	/**
	 * Ư¡ ���� ����
	 * @param param
	 * @return
	 */
	@Override
	public void deleteCharacter(Map<String, Object> param)  throws Exception{
		if(this.checkUserCd(param)) {
			characterMapper.deleteCharacter(param);
		}else {
			throw new Exception();
		}
		
	}

	/**
	 * �Խñ�  �ۼ��� ���� Ȯ��
	 * @param param
	 * @return
	 */
	private boolean checkUserCd(Map<String, Object> param) throws Exception{
		List<Map<String, Object>> result = characterMapper.selectCharacterByCd(param);
		if(CollectionUtils.isEmpty(result)) {
			throw new Exception();
		}
		UserDetailsDto userInfo = (UserDetailsDto)SessionUtility.getUserDetails();
		if(Long.compare((long) result.get(0).get("user_cd"), userInfo.getUserCd()) != 0 ) {
			return false;
		}
		
		return true;
	}

	private void setFiles(Map<String, Object> detail) {
		if(detail.get("file_grp_id") != null) {
			Long file_grp_id = (Long)detail.get("file_grp_id");
			List<Map<String, Object>> fileList = fileMapper.selectFilesAllForGrup(file_grp_id);
			if(!CollectionUtils.isEmpty(fileList)) {
				detail.put("fileList", fileList);
			}
		}
	}
	
}
