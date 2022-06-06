package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatGroupMapper")
public interface CatGroupMapper {

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

	/**
	 * ����� �׷� ���
	 * @param data
	 */
	public void insertCatGroup(Map<String, Object> data);

	/**
	 * ����� �׷� ��� ���
	 * @param data
	 */
	public void insertCatGroupMember(Map<String, Object> data);

	/**
	 * ����� �׷� ����
	 * @param data
	 */
	public void deleteCatGroup(Map<String, Object> data);

	/**
	 * ����� �׷� ��� ����
	 * @param data
	 */
	public void deleteCatGroupMember(Map<String, Object> data);

	
}
