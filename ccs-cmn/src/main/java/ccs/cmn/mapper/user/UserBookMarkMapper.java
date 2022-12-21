package ccs.cmn.mapper.user;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "UserBookMarkMapper")
public interface UserBookMarkMapper {

	/**
	 * ����� ���ã�� ���  ��ȸ
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
