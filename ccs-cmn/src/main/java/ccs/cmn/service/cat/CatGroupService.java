package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatGroupService {

	/**
	 * ����� �׷� ��� ���� ��ȸ 
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupMemberList(Map<String, Object> data);

	/**
	 * ����� �׷� ���� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data);

}
