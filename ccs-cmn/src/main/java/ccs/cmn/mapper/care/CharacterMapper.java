package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CharacterMapper")
public interface CharacterMapper {

	/**
	 * ����� Ư¡ ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterList(Map<String, Object> param);

	/**
	 * ����� Ư¡ ���� ��ȸ - �ܰ�
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCharacterByCd(Map<String, Object> param);

	/**
	 * Ư¡ ���� ����
	 * @param param
	 * @return
	 */
	public void insertCharacter(Map<String, Object> param);

	/**
	 * Ư¡ ���� ����
	 * @param param
	 * @return
	 */
	public void updateCharacter(Map<String, Object> param);

	/**
	 * Ư¡ ���� ����
	 * @param param
	 * @return
	 */
	public void deleteCharacter(Map<String, Object> param);

}
