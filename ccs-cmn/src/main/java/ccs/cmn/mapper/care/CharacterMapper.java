package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CharacterMapper")
public interface CharacterMapper {

	/**
	 * 고양이 특징 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterList(Map<String, Object> param);

	/**
	 * 고양이 특징 정보 조회 - 단건
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterByCd(Map<String, Object> param);

	/**
	 * 특징 정보 저장
	 * @param param
	 * @return
	 */
	public void insertCharacter(Map<String, Object> param);

	/**
	 * 특징 정보 수정
	 * @param param
	 * @return
	 */
	public void updateCharacter(Map<String, Object> param);

	/**
	 * 특징 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteCharacter(Map<String, Object> param);

}
