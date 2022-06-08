package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatProfileMapper")
public interface CatProfileMapper {

	/**
	 * 고양이 프로필 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatProfile(Map<String, Object> param);

	/**
	 * 고양이 무리 프로필 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatGrpProfile(Map<String, Object> param);

	
	/**
	 * 고양이 건강 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);

	/**
	 * 돌봄 갯수(여부) 조회
	 * @param param
	 * @return
	 */
	public Integer selectCareCount(Map<String, Object> param);

	/**
	 * 돌봄 삭제
	 * @param param
	 */
	public void deleteCare(Map<String, Object> param);

	/**
	 * 돌봄 등록
	 * @param param
	 */
	public void insertCare(Map<String, Object> param);

	/**
	 * 즐겨찾기 갯수(여부) 조회
	 * @param param
	 * @return
	 */
	public Integer selectBookMarkCount(Map<String, Object> param);

	/**
	 * 즐겨찾기 삭제
	 * @param param
	 */
	public void deleteBookMark(Map<String, Object> param);

	/**
	 * 즐겨찾기 등록
	 * @param param
	 */
	public void insertBookMark(Map<String, Object> param);
	
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
