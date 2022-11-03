package ccs.cmn.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ccs.cmn.mapper.CmnMapper;
import ccs.cmn.mapper.FileMapper;
import ccs.cmn.mapper.user.UserProfileMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.user.UserProfileService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;


@Service(value="UserProfileService")
public class UserProfileServiceImpl implements UserProfileService {

	@Resource(name="UserProfileMapper")
	private UserProfileMapper userProfileMapper;

	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectUserProfile(Map<String, Object> param) {
		List<Map<String, Object>> list = userProfileMapper.selectUserProfile(param);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * ����� ������ ���� ��ȸ (÷������ ����Ʈ ����)
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectUserProfileDetail(Map<String, Object> param) throws Exception {
		List<Map<String, Object>> list = userProfileMapper.selectUserProfile(param);
		if(!CollectionUtils.isEmpty(list)) {
			Map<String, Object> detail = list.get(0);
			//÷������ ����
			if(detail.get("file_grp_id") != null) {
				Long file_grp_id = (Long)detail.get("file_grp_id");
				List<Map<String, Object>> fileList = fileService.selectFiles(file_grp_id);
				if(!CollectionUtils.isEmpty(fileList)) {
					detail.put("fileList", fileList);
				}
			}
			return detail;
		}
		return null;
	}
	/**
	 * ����� ������ ���� ���� 
	 * @param param
	 * @throws Exception 
	 */
	@Override
	public void saveUserProfile(Map<String, Object> param, FileParameter fileParameter) throws Exception {
		if(this.checkUserCd(param)) {
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
			//������ ������Ʈ
			userProfileMapper.updateNickname(param);
			userProfileMapper.updateProfile(param);
		}
	}
	
	/**
	 * �г��� �ߺ� üũ
	 * @return
	 */
	@Override
	public boolean checkNickname(Map<String, Object> param) {
		if(userProfileMapper.selectNicknameCount(param) > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * ����� ������ ���� ���� ����
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean checkUserCd(Map<String, Object> param) throws Exception{
		UserDetailsDto userInfo = (UserDetailsDto)SessionUtility.getUserDetails();
		Integer user_cd = (Integer)param.get("user_cd");
		if(Long.compare( user_cd.longValue(), userInfo.getUserCd()) != 0 ) {
			return false;
		}
		return true;
	}


}
