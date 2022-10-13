package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface CareService {

	/**
	 * ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareList(Map<String, Object> param);

	/**
	 *  ���� ��� or ����
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveCare(Map<String, Object> param);

}
