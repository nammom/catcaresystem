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
	 * ���ã�� ����(����) ��ȸ
	 * @param param
	 * @return
	 */
	public Integer selectBookMarkCount(Map<String, Object> param);

	/**
	 * ���ã�� ����
	 * @param param
	 */
	public void deleteBookMark(Map<String, Object> param);

	/**
	 * ���ã�� ���
	 * @param param
	 */
	public void insertBookMark(Map<String, Object> param);
	
	/**
	 * ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);
	
	/**
	 * ���ã�� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);

}
