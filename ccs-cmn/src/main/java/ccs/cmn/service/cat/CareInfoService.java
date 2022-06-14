package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface CareInfoService {

	/**
	 * 고양이 돌봄 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareInfoList(Map<String, Object> param);

	/**
	 * 돌봄 정보 저장(수정)
	 * @param param
	 * @return
	 */
	public Map<String, Object> insertCareInfo(Map<String, Object> param)  throws Exception;

	/**
	 * 돌봄 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteCareInfo(Map<String, Object> param)  throws Exception;

}
