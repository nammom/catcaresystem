package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface CharacterService {

	/**
	 * ����� Ư¡ ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterList(Map<String, Object> param) throws Exception ;

	/**
	 * ����� Ư¡ �� ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCharacterDetail(Map<String, Object> param) throws Exception;

	/**
	 * Ư¡ ���� ����(����)
	 * @param param
	 * @return
	 */
	public void insertCharacter(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * Ư¡ ���� ����
	 * @param param
	 * @return
	 */
	public void deleteCharacter(Map<String, Object> param) throws Exception;



}
