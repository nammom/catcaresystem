package ccs.cmn.service.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface HabitatService {

	/**
	 * ������ ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data);

	/**
	 * ������ �� ��ȸ
	 * @param data
	 * @return
	 */
	public Map<String, Object> selectHabitatDetail(Map<String, Object> data) throws Exception;

	/**
	 * ������ ������ ��ȸ
	 * @param data
	 * @return
	 */
	public Map<String, Object> selectHabitatProfile(Map<String, Object> data);

	/**
	 * ������ ����(����)
	 * @param param
	 * @param fileParameter
	 */
	public Map<String, Object> insertHabitat(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * ������ ����
	 * @param param
	 */
	public void deleteHabitat(Map<String, Object> param) throws Exception;

}
