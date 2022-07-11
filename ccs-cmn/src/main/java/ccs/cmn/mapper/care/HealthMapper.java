package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "HealthMapper")
public interface HealthMapper {

	/**
	 * 고양이 건강 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthList(Map<String, Object> param);

	/**
	 * 고양이 건강 정보 조회 - 단건
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthByCd(Map<String, Object> param);

	/**
	 * 건강 정보 저장
	 * @param param
	 * @return
	 */
	public void insertHealth(Map<String, Object> param);

	/**
	 * 건강 정보 수정
	 * @param param
	 * @return
	 */
	public void updateHealth(Map<String, Object> param);

	/**
	 * 건강 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteHealth(Map<String, Object> param);

}
