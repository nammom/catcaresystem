package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "BookMarkMapper")
public interface BookMarkMapper {

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
	 * 즐겨찾기 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);


}
