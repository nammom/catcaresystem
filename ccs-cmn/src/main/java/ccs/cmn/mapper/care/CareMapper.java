package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CareMapper")
public interface CareMapper {

	/**
	 * ���� ����(����) ��ȸ
	 * @param param
	 * @return
	 */
	public Integer selectCareCount(Map<String, Object> param);

	/**
	 * ���� ����
	 * @param param
	 */
	public void deleteCare(Map<String, Object> param);

	/**
	 * ���� ���
	 * @param param
	 */
	public void insertCare(Map<String, Object> param);

	/**
	 * ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);


}
