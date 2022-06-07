package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;


import ccs.framework.annotations.Mapper;


@Mapper(value="CmnMapper")
public interface CmnMapper {

	/**
	 * ���� �ڵ� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param);

	/**
	 * ���� �ڵ� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectAreaCodeList(Map<String, Object> param);

	/**
	 * ����� ���� ��ȸ
	 * @param target_cd
	 * @return
	 */
	public List<Map<String, Object>> selectCatArea(Long target_cd);

	/**
	 * ����� �˻� ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectSearchCatList(Map<String, Object> data);


	/**
	 * ������ �˻� ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectSearchHabitatList(Map<String, Object> data);


}
