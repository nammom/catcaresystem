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
import ccs.cmn.mapper.manage.CatMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.manage.CatService;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;

@Service(value="CatService")
public class CatServiceImpl implements CatService {

	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="CatMapper")
	private CatMapper catMapper;

	@Resource(name="FileMapper")
	private FileMapper fileMapper;
	
	/**
	 * 고양이 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return catMapper.selectCatList(data);
	}

	/**
	 * 고양이 상세 조회
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> selectCatDetail(Map<String, Object> data) throws Exception {
		
		//서식지 정보 조회
		List<Map<String, Object>> catList = catMapper.selectCatByCd(data);
		if(!CollectionUtils.isEmpty(catList)) {
			Map<String, Object> detail = catList.get(0);
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
	 * 고양이 저장(수정)
	 * @param param
	 * @param fileParameter
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> insertCat(Map<String, Object> param, FileParameter fileParameter) throws Exception {
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

		if(ObjectUtils.isEmpty(param.get("cat_cd"))){
			catMapper.insertCat(param);
			param.put("cat_cd", Long.parseLong((String)param.get("cat_cd")));
			
			if(this.isGroup(param)) {
				catMapper.insertCatGrpProfile(param);
			} else {
				catMapper.insertCatProfile(param);
			}
		}else {
			if(this.checkUserCd(param)) {
				catMapper.updateCat(param);
				
				if(this.isGroup(param)) {
					catMapper.updateCatGrpProfile(param);
				} else {
					catMapper.updateCatProfile(param);
				}
			}else {
				throw new Exception();
			}
		}
		
		return param;
	}

	/**
	 * 고양이 삭제
	 * @param param
	 */
	@Override
	public void deleteCat(Map<String, Object> param) throws Exception{
		if(this.checkUserCd(param)) {
			catMapper.deleteCat(param);
			
			if(this.isGroup(param)) {
				catMapper.deleteCatGrpProfile(param);
			} else {
				catMapper.deleteCatProfile(param);
			}
		}else {
			throw new Exception();
		}
	}
	
	public boolean isGroup(Map<String, Object> param) throws Exception{
		String group_yn = (String)param.get("group_yn");
		if(StringUtils.isNotEmpty(group_yn)) {
			if(StringUtils.equals(group_yn, "Y")) {
				return true;
			}
			return false;
		} else {
			List<Map<String, Object>> result = catMapper.selectCatByCd(param);
			if(CollectionUtils.isEmpty(result)) {
				throw new Exception();
			}
			if(StringUtils.equals((String) result.get(0).get("group_yn"), "Y") ) {
				return true;
			} 
			return false;
		}
	}
	
	/**
	 * 고양이 그룹에 소속된 고양이 수 체크
	 * @param param
	 * @return
	 */
	public boolean checkCatMemberCount(Map<String, Object> param) {
		Integer count = catMapper.selectCatGrpMemberCount(param);
		if(count > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 게시글  작성자 여부 확인
	 * @param param
	 * @return
	 */
	private boolean checkUserCd(Map<String, Object> param) throws Exception{
		List<Map<String, Object>> result = catMapper.selectCatByCd(param);
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
