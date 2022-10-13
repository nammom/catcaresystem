package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatProfileMapper")
public interface CatProfileMapper {

	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatProfile(Map<String, Object> param);

	/**
	 * ����� ���� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatGrpProfile(Map<String, Object> param);

	
	/**
	 * ����� �ǰ� ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);

	/**
	 * ����� ��Ī ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatName(Map<String, Object> param);

	/**
	 * ����� ��Ī ����
	 * @param param
	 */
	public Integer deleteCatName(Map<String, Object> param);

	/**
	 * ����� ��Ī ����
	 * @param param
	 * @return
	 */
	public Integer insertCatName(Map<String, Object> param);

}
