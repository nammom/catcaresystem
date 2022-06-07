package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatHabitatService {

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
