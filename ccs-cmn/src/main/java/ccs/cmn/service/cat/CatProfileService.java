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
	 *  돌봄 등록 or 해제
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveCare(Map<String, Object> param);
	
	/**
	 *  즐겨찾기 등록 or 해제
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveBookMark(Map<String, Object> param);

	/**
	 * 돌봄 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);

	/**
	 * 즐겨찾기 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);
}
