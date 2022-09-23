package ccs.cmn.mapper.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value="CatMapper")
public interface CatMapper {

	/**
	 * ����� ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatList(Map<String, Object> data);

	/**
	 * ����� �� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatByCd(Map<String, Object> data);

	/**
	 * ���� ����̿� �Ҽӵ� ����� ��
	 * @param data
	 * @return
	 */
	public Integer selectCatGrpMemberCount(Map<String, Object> data);

	/**
	 * ����� ����
	 * @param param
	 */
	public void insertCat(Map<String, Object> param);

	/**
	 * ����� ������ ����
	 * @param param
	 */
	public void insertCatProfile(Map<String, Object> param);

	/**
	 * ����� �׷� ������ ����
	 * @param param
	 */
	public void insertCatGrpProfile(Map<String, Object> param);

	/**
	 * ����� ����
	 * @param param
	 */
	public void updateCat(Map<String, Object> param);

	/**
	 * ����� ������ ����
	 * @param param
	 */
	public void updateCatProfile(Map<String, Object> param);

	/**
	 * ����� �׷� ������ ����
	 * @param param
	 */
	public void updateCatGrpProfile(Map<String, Object> param);

	/**
	 * ����� ����
	 * @param param
	 */
	public void deleteCat(Map<String, Object> param);

	/**
	 * ����� ������ ����
	 * @param param
	 */
	public void deleteCatProfile(Map<String, Object> param);

	/**
	 * ����� �׷� ������ ����
	 * @param param
	 */
	public void deleteCatGrpProfile(Map<String, Object> param);

}
