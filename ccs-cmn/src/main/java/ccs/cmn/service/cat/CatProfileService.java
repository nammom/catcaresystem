package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatProfileService {

	/**
	 * 고양이 프로필 정보 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCatProfile(Map<String, Object> param);

	/**
	 * 고양이 건강 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);

	/**
	 * 고양이 애칭 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCatName(Map<String, Object> param);

	/**
	 * 고양이 애칭 저장(수정)
	 * @param param
	 * @return
	 */
	public Integer saveCatName(Map<String, Object> param);

}
