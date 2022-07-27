package ccs.cmn.service;

import java.util.List;
import java.util.Map;

public interface CmnService {

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
	public Map<String, Object> selectCatArea(Long target_cd);

	/**
	 * ����� ���� ���� ��ȸ
	 * @param target_cd
	 * @return
	 */
	public String selectCatGroupYn(Long target_cd);
	
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

	/**
	 * �׺���̼� �޴� ��� ��ȸ
	 * @return
	 */
	public List<Map<String, Object>> selectMenuList();
	
	/**
	 * �׺���̼� ����� etc �޴� ��� ��ȸ
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectEtcMenuList(Map<String, Object> data);

}
