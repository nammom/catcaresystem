package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatHabitatService {

	/**
	 * 고양이 서식지 정보 조회 
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatHabitatList(Map<String, Object> data);

	/**
	 * 고양이 서식지 등록
	 * @param data
	 */
	public void insertCatHabitat(Map<String, Object> data);

	/**
	 *  고양이 서식지 삭제
	 * @param data
	 */
	public void deleteCatHabitat(Map<String, Object> data);

}
