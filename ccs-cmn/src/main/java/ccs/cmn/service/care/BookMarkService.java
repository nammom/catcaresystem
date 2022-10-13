package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface BookMarkService {

	/**
	 * ���ã�� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);

	/**
	 *  ���ã�� ��� or ����
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveBookMark(Map<String, Object> param);

}
