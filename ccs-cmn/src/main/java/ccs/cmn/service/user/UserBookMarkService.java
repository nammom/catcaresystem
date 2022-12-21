package ccs.cmn.service.user;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface UserBookMarkService {

	/**
	 * ����� ����� ���ã�� ���  ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserCatBookMarkList(Map<String, Object> data);

	/**
	 * ����� ������ ���ã�� ���  ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserHabitatBookMarkList(Map<String, Object> data);
	
}
