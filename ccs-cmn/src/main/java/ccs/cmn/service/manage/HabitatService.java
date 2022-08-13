package ccs.cmn.service.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface HabitatService {

	/**
	 * 서식지 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectHabitatList(Map<String, Object> data);

	/**
	 * 서식지 상세 조회
	 * @param data
	 * @return
	 */
	public Map<String, Object> selectHabitatDetail(Map<String, Object> data) throws Exception;

	/**
	 * 서식지 프로필 조회
	 * @param data
	 * @return
	 */
	public Map<String, Object> selectHabitatProfile(Map<String, Object> data);

	/**
	 * 서식지 저장(수정)
	 * @param param
	 * @param fileParameter
	 */
	public Map<String, Object> insertHabitat(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 서식지 삭제
	 * @param param
	 */
	public void deleteHabitat(Map<String, Object> param) throws Exception;

}
