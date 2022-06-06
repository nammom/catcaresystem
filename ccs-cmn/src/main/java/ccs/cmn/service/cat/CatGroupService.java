package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatGroupService {

	/**
	 * ����� �׷� ���� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data);

	/**
	 * ����� �׷� ���
	 * @param data
	 */
	public void insertCatGroup(Map<String, Object> data);

	/**
	 * ����� �׷� ����
	 * @param data
	 */
	public void deleteCatGroup(Map<String, Object> data);

}
