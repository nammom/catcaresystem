package ccs.cmn.mapper.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value="HabitatMapper")
public interface HabitatMapper {

	/**
	 * 서식지 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data);

	/**
	 * 서식지 상세 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatByCd(Map<String, Object> data);

	/**
	 * 서식지 저장
	 * @param param
	 */
	public void insertHabitat(Map<String, Object> param);

	/**
	 * 서식지 수정
	 * @param param
	 */
	public void updateHabitat(Map<String, Object> param);

	/**
	 * 서식지 삭제
	 * @param param
	 */
	public void deleteHabitat(Map<String, Object> param);

}
