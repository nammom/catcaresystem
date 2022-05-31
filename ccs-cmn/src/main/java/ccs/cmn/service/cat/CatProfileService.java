package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatProfileService {

	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCatProfile(Map<String, Object> param);

	/**
	 * ����� �ǰ� ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);

	/**
	 *  ���� ��� or ����
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveCare(Map<String, Object> param);
	
	/**
	 *  ���ã�� ��� or ����
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveBookMark(Map<String, Object> param);

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
