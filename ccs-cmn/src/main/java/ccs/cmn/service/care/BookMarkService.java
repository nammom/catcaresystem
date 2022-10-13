package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ccs.framework.model.FileParameter;

public interface BookMarkService {

	/**
	 * 즐겨찾기 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);

	/**
	 *  즐겨찾기 등록 or 해제
	 * @param param
	 * @return
	 */
	public Map<String, Object> saveBookMark(Map<String, Object> param);

}
