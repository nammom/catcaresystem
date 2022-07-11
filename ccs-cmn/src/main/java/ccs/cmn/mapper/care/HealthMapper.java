package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "HealthMapper")
public interface HealthMapper {

	/**
	 * ����� �ǰ� ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthList(Map<String, Object> param);

	/**
	 * ����� �ǰ� ���� ��ȸ - �ܰ�
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthByCd(Map<String, Object> param);

	/**
	 * �ǰ� ���� ����
	 * @param param
	 * @return
	 */
	public void insertHealth(Map<String, Object> param);

	/**
	 * �ǰ� ���� ����
	 * @param param
	 * @return
	 */
	public void updateHealth(Map<String, Object> param);

	/**
	 * �ǰ� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteHealth(Map<String, Object> param);

}
