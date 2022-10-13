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
	 * ����� ��Ī ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectCatName(Map<String, Object> param);

	/**
	 * ����� ��Ī ����(����)
	 * @param param
	 * @return
	 */
	public Integer saveCatName(Map<String, Object> param);

}
