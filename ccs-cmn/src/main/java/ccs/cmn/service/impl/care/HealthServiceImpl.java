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
import ccs.cmn.mapper.care.HealthMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.care.HealthService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;


@Service(value="HealthService")
public class HealthServiceImpl implements HealthService {

	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="HealthMapper")
	private HealthMapper healthMapper;

	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	
	/**
	 * 고양이 건강 정보 목록 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<Map<String, Object>> selectHealthList(Map<String, Object> param) throws Exception {
		List<Map<String, Object>> healthList = healthMapper.selectHealthList(param);
		for(Map<String, Object> e : healthList) {
			//첨부파일 셋팅
			if(e.get("file_grp_id") != null) {
				Long file_grp_id = (Long)e.get("file_grp_id");
				List<Map<String, Object>> fileList = fileMapper.selectFilesAllForGrup(file_grp_id);
				if(!CollectionUtils.isEmpty(fileList)) {
					e.put("fileList", fileList);
				}
			}
			e.put("_SESSION_USER_CD_", param.get("_SESSION_USER_CD_"));
		}
		return healthList;
	}
	
	/**
	 * 고양이 건강 상세 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectHealthDetail(Map<String, Object> param) throws Exception {
		
		//급여정보 조회
		List<Map<String, Object>> healthList = healthMapper.selectHealthByCd(param);
		if(!CollectionUtils.isEmpty(healthList)) {
			Map<String, Object> detail = healthList.get(0);
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
	 * 건강 정보 저장(수정)
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertHealth(Map<String, Object> param, FileParameter fileParameter) throws Exception{
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

		if(ObjectUtils.isEmpty(param.get("health_cd"))){
			healthMapper.insertHealth(param);
		}else {
			if(this.checkUserCd(param)) {
				healthMapper.updateHealth(param);
			}else {
				throw new Exception();
			}
		}
	}

	/**
	 * 건강 정보 삭제
	 * @param param
	 * @return
	 */
	@Override
	public void deleteHealth(Map<String, Object> param) throws Exception{
		if(this.checkUserCd(param)) {
			healthMapper.deleteHealth(param);
		}else {
			throw new Exception();
		}
		
	}

	/**
	 * 게시글  작성자 여부 확인
	 * @param param
	 * @return
	 */
	private boolean checkUserCd(Map<String, Object> param) throws Exception{
		List<Map<String, Object>> result = healthMapper.selectHealthByCd(param);
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
