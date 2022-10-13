package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface CareService {

	/**
	 * 돌봄 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);

	/**
	 *  돌봄 등록 or 해제
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveCare(Map<String, Object> param);

}
