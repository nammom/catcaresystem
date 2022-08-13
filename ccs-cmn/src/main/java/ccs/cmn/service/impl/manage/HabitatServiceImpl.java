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
	 * 서식지 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return habitatMapper.selectHabitatList(data);
	}

	/**
	 * 서식지 상세 조회
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectHabitatDetail(Map<String, Object> data) throws Exception {
		
		//서식지 정보 조회
		List<Map<String, Object>> habitatList = habitatMapper.selectHabitatByCd(data);
		if(!CollectionUtils.isEmpty(habitatList)) {
			Map<String, Object> detail = habitatList.get(0);
			//첨부파일 셋팅
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
	 * 서식지 프로필 조회
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectHabitatProfile(Map<String, Object> data) {
		//서식지 정보 조회
		List<Map<String, Object>> habitatList = habitatMapper.selectHabitatByCd(data);
		if(!CollectionUtils.isEmpty(habitatList)) {
			return habitatList.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * 서식지 저장(수정)
	 * @param param
	 * @param fileParameter
	 * @throws Exception 
	 */
	@Transactional
	@Override
	public Map<String, Object> insertHabitat(Map<String, Object> param, FileParameter fileParameter) throws Exception {
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
	 * 서식지 삭제
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
	 * 게시글  작성자 여부 확인
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
