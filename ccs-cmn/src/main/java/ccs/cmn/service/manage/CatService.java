package ccs.cmn.service.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface CatService {

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
	public Map<String, Object> selectCatDetail(Map<String, Object> data) throws Exception;

	/**
	 * ����� ����(����)
	 * @param param
	 * @param fileParameter
	 */
	public Map<String, Object> insertCat(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * ����� ����
	 * @param param
	 */
	public void deleteCat(Map<String, Object> param) throws Exception;

}
