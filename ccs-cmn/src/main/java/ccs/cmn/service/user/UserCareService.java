package ccs.cmn.service.user;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface UserCareService {

	/**
	 * ����� ����� ���� ���  ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserCatCareList(Map<String, Object> data);

	/**
	 * ����� ������ ���� ���  ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserHabitatCareList(Map<String, Object> data);
	
}
