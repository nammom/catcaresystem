package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatGroupService {

	/**
	 * 고양이 그룹 정보 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data);

	/**
	 * 고양이 그룹 등록
	 * @param data
	 */
	public void insertCatGroup(Map<String, Object> data);

	/**
	 * 고양이 그룹 삭제
	 * @param data
	 */
	public void deleteCatGroup(Map<String, Object> data);

}
