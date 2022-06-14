package ccs.cmn.service.impl.cat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import ccs.cmn.mapper.cat.CatGroupMapper;
import ccs.cmn.mapper.cat.CareInfoMapper;
import ccs.cmn.service.cat.CatGroupService;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;
import ccs.cmn.service.cat.CareInfoService;


@Service(value="CareInfoService")
public class CareInfoServiceImpl implements CareInfoService {

	@Resource(name="CareInfoMapper")
	CareInfoMapper careInfoMapper;

	/**
	 * 고양이 돌봄 정보 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCareInfoList(Map<String, Object> param) {
		return careInfoMapper.selectCareInfoList(param);
	}

	/**
	 * 돌봄 정보 저장(수정)
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> insertCareInfo(Map<String, Object> param) throws Exception{
		if(ObjectUtils.isEmpty(param.get("catcare_cd"))){
			careInfoMapper.insertCareInfo(param);
		}else {
			if(this.checkUserCd(param)) {
				careInfoMapper.updateCareInfo(param);
			}else {
				throw new Exception();
			}
		}
		return param;
	}

	/**
	 * 돌봄 정보 삭제
	 * @param param
	 * @return
	 */
	@Override
	public void deleteCareInfo(Map<String, Object> param)  throws Exception{
		if(this.checkUserCd(param)) {
			careInfoMapper.deleteCareInfo(param);
		}else {
			throw new Exception();
		}
		
	}

	private boolean checkUserCd(Map<String, Object> param) {
		List<Map<String, Object>> result = careInfoMapper.selectCareInfoList(param);
		if(CollectionUtils.isEmpty(result)) {
			return false;
		}
		UserDetailsDto userInfo = (UserDetailsDto)SessionUtility.getUserDetails();
		if(Long.compare((long) result.get(0).get("user_cd"), userInfo.getUserCd()) != 0 ) {
			return false;
		}
		
		return true;
	}


}
