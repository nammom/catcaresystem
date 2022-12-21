package ccs.cmn.service.user;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface UserCareService {

	/**
	 * 사용자 고양이 돌봄 목록  조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserCatCareList(Map<String, Object> data);

	/**
	 * 사용자 서식지 돌봄 목록  조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectUserHabitatCareList(Map<String, Object> data);
	
}
