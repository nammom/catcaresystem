package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface HealthService {

	/**
	 * 고양이 건강 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectHealthList(Map<String, Object> param) throws Exception ;

	/**
	 * 고양이 건강 상세 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectHealthDetail(Map<String, Object> param) throws Exception;

	/**
	 * 건강 정보 저장(수정)
	 * @param param
	 * @return
	 */
	public void insertHealth(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 건강 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteHealth(Map<String, Object> param) throws Exception;



}
