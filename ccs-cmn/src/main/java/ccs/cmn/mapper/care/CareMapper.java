package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CareMapper")
public interface CareMapper {

	/**
	 * µ¹º½ °¹¼ö(¿©ºÎ) Á¶È¸
	 * @param param
	 * @return
	 */
	public Integer selectCareCount(Map<String, Object> param);

	/**
	 * µ¹º½ »èÁ¦
	 * @param param
	 */
	public void deleteCare(Map<String, Object> param);

	/**
	 * µ¹º½ µî·Ï
	 * @param param
	 */
	public void insertCare(Map<String, Object> param);

	/**
	 * µ¹º½ ¸ñ·Ï Á¶È¸
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);


}
