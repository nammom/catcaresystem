package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface CareInfoService {

	/**
	 * ����� ���� ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareInfoList(Map<String, Object> param);

	/**
	 * ���� ���� ����(����)
	 * @param param
	 * @return
	 */
	public Map<String, Object> insertCareInfo(Map<String, Object> param)  throws Exception;

	/**
	 * ���� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteCareInfo(Map<String, Object> param)  throws Exception;

}
