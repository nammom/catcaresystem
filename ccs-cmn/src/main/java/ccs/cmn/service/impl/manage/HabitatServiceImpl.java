package ccs.cmn.service.impl.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import ccs.cmn.mapper.FileMapper;
import ccs.cmn.mapper.manage.HabitatMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.manage.HabitatService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;

@Service(value="HabitatService")
public class HabitatServiceImpl implements HabitatService {

	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="HabitatMapper")
	private HabitatMapper habitatMapper;

	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	
	/**
	 * ������ ��� ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return habitatMapper.selectHabitatList(data);
	}

	/**
	 * ������ �� ��ȸ
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectHabitatDetail(Map<String, Object> data) throws Exception {
		
		//������ ���� ��ȸ
		List<Map<String, Object>> habitatList = habitatMapper.selectHabitatByCd(data);
		if(!CollectionUtils.isEmpty(habitatList)) {
			Map<String, Object> detail = habitatList.get(0);
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
	 * ������ ������ ��ȸ
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectHabitatProfile(Map<String, Object> data) {
		//������ ���� ��ȸ
		List<Map<String, Object>> habitatList = habitatMapper.selectHabitatByCd(data);
		if(!CollectionUtils.isEmpty(habitatList)) {
			return habitatList.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * ������ ����(����)
	 * @param param
	 * @param fileParameter
	 * @throws Exception 
	 */
	@Transactional
	@Override
	public Map<String, Object> insertHabitat(Map<String, Object> param, FileParameter fileParameter) throws Exception {
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

		if(ObjectUtils.isEmpty(param.get("habitat_cd"))){
			habitatMapper.insertHabitat(param);
		}else {
			if(this.checkUserCd(param)) {
				habitatMapper.updateHabitat(param);
			}else {
				throw new Exception();
			}
		}
		
		return param;
	}

	/**
	 * ������ ����
	 * @param param
	 */
	@Override
	public void deleteHabitat(Map<String, Object> param) throws Exception{
		if(this.checkUserCd(param)) {
			habitatMapper.deleteHabitat(param);
		}else {
			throw new Exception();
		}
	}
	
	/**
	 * �Խñ�  �ۼ��� ���� Ȯ��
	 * @param param
	 * @return
	 */
	private boolean checkUserCd(Map<String, Object> param) {
		List<Map<String, Object>> result = habitatMapper.selectHabitatByCd(param);
		if(CollectionUtils.isEmpty(result)) {
			return false;
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
