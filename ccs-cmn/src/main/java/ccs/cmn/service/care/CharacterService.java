package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface CharacterService {

	/**
	 * 고양이 특징 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterList(Map<String, Object> param) throws Exception ;

	/**
	 * 고양이 특징 상세 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCharacterDetail(Map<String, Object> param) throws Exception;

	/**
	 * 특징 정보 저장(수정)
	 * @param param
	 * @return
	 */
	public void insertCharacter(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 특징 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteCharacter(Map<String, Object> param) throws Exception;



}
