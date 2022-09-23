package ccs.cmn.service.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface CatService {

	/**
	 * 고양이 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatList(Map<String, Object> data);

	/**
	 * 고양이 상세 조회
	 * @param data
	 * @return
	 */
	public Map<String, Object> selectCatDetail(Map<String, Object> data) throws Exception;

	/**
	 * 고양이 저장(수정)
	 * @param param
	 * @param fileParameter
	 */
	public Map<String, Object> insertCat(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 고양이 삭제
	 * @param param
	 */
	public void deleteCat(Map<String, Object> param) throws Exception;

}
