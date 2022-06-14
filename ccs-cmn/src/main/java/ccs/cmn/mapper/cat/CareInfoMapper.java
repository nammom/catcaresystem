package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CareInfoMapper")
public interface CareInfoMapper {

	/**
	 * ����� ���� ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareInfoList(Map<String, Object> param);

	/**
	 * ���� ���� ���
	 * @param param
	 * @return
	 */
	public void insertCareInfo(Map<String, Object> param);

	/**
	 * ���� ���� ����
	 * @param param
	 */
	public void updateCareInfo(Map<String, Object> param);

	/**
	 * ���� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteCareInfo(Map<String, Object> param);


	
}
