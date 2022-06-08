package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;


import ccs.framework.annotations.Mapper;


@Mapper(value="CmnMapper")
public interface CmnMapper {

	/**
	 * 공통 코드 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param);

	/**
	 * 지역 코드 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectAreaCodeList(Map<String, Object> param);

	/**
	 * 고양이 무리 여부 조회
	 * @param target_cd
	 * @return
	 */
	public String selectCatGroupYn(Long target_cd);

	/**
	 * 고양이 지역 조회
	 * @param target_cd
	 * @return
	 */
	public List<Map<String, Object>> selectCatArea(Long target_cd);

	/**
	 * 고양이 검색 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectSearchCatList(Map<String, Object> data);


	/**
	 * 서식지 검색 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectSearchHabitatList(Map<String, Object> data);


}
