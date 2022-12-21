package ccs.cmn.mapper.user;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "UserCareMapper")
public interface UserCareMapper {

	/**
	 * ����� ���� ���  ��ȸ
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
