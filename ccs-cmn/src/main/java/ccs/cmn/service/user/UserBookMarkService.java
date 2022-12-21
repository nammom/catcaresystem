package ccs.cmn.service.user;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface UserBookMarkService {

	/**
	 * 사용자 고양이 즐겨찾기 목록  조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserCatBookMarkList(Map<String, Object> data);

	/**
	 * 사용자 서식지 즐겨찾기 목록  조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserHabitatBookMarkList(Map<String, Object> data);
	
}
