package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface FeedService {

	/**
	 * 고양이 급여 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedList(Map<String, Object> param);

	/**
	 * 고양이 급여 상세 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectFeedDetail(Map<String, Object> param);

	/**
	 * 급여 정보 저장(수정)
	 * @param param
	 * @return
	 */
	public void insertFeed(Map<String, Object> param)  throws Exception;

	/**
	 * 급여 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteFeed(Map<String, Object> param)  throws Exception;


}
