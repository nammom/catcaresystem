package ccs.cmn.mapper.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value="HabitatMapper")
public interface HabitatMapper {

	/**
	 * ������ ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data);

	/**
	 * ������ �� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatByCd(Map<String, Object> data);

	/**
	 * ������ ����
	 * @param param
	 */
	public void insertHabitat(Map<String, Object> param);

	/**
	 * ������ ����
	 * @param param
	 */
	public void updateHabitat(Map<String, Object> param);

	/**
	 * ������ ����
	 * @param param
	 */
	public void deleteHabitat(Map<String, Object> param);

}
