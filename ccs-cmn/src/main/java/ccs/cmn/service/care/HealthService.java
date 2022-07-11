package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface HealthService {

	/**
	 * ����� �ǰ� ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthList(Map<String, Object> param) throws Exception ;

	/**
	 * ����� �ǰ� �� ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectHealthDetail(Map<String, Object> param) throws Exception;

	/**
	 * �ǰ� ���� ����(����)
	 * @param param
	 * @return
	 */
	public void insertHealth(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * �ǰ� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteHealth(Map<String, Object> param) throws Exception;



}
