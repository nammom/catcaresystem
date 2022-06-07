package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatHabitatMapper")
public interface CatHabitatMapper {

	/**
	 * ����� ������ ���� ��ȸ 
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatHabitatList(Map<String, Object> data);

	/**
	 * ����� ������ ���
	 * @param data
	 */
	public void insertCatHabitat(Map<String, Object> data);

	/**
	 *  ����� ������ ����
	 * @param data
	 */
	public void deleteCatHabitat(Map<String, Object> data);

	
}
