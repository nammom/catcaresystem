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
	 * 사용자 프로필 정보 조회
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
	 * 사용자 프로필 정보 조회 (첨부파일 리스트 포함)
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectUserProfileDetail(Map<String, Object> param) throws Exception {
		List<Map<String, Object>> list = userProfileMapper.selectUserProfile(param);
		if(!CollectionUtils.isEmpty(list)) {
			Map<String, Object> detail = list.get(0);
			//첨부파일 셋팅
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
	 * 사용자 프로필 정보 수정 
	 * @param param
	 * @throws Exception 
	 */
	@Override
	public void saveUserProfile(Map<String, Object> param, FileParameter fileParameter) throws Exception {
		if(this.checkUserCd(param)) {
			//----------------------------------------------------------- 파일 정보 업데이트 ---------------------------------------------

			// 업로드한 파일정보 
			List<FileInfoVO> uploadedFiles = fileParameter.getFiles();
			// 기존에 있었지만 삭제된 파일 정보 목록
			List<Map<String,Object>> filesToDelete = (List<Map<String,Object>>)param.get("deleteFiles");
			
			// 기존에 파일 그룹정보가 있으면 업데이트를 위해서 파라미터로 정보 가지고 오기
			Long FILE_GRP_ID = null;
			if(StringUtils.isNotEmpty((String)param.get("file_grp_id"))) {
				FILE_GRP_ID = Long.parseLong((String)param.get("file_grp_id"));
			}
			FILE_GRP_ID = uploadFileService.uploadFiles(param, FILE_GRP_ID, uploadedFiles, filesToDelete);
			
			param.put("file_grp_id", FILE_GRP_ID);

			//----------------------------------------------------------- 파일 정보 업데이트 ---------------------------------------------
			//프로필 업데이트
			userProfileMapper.updateNickname(param);
			userProfileMapper.updateProfile(param);
		}
	}
	
	/**
	 * 닉네임 중복 체크
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
	 * 사용자 프로필 수정 권한 여부
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
