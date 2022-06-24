package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "FeedMapper")
public interface FeedMapper {

	/**
	 * 고양이 급여 정보 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedList(Map<String, Object> param);
	
	/**
	 * 고양이 급여 갯수 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedCountList(Map<String, Object> param);
	
	/**
	 * 고양이 급여 정보 조회 - 단건
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedByCd(Map<String, Object> param);

	/**
	 * 고양이 급여 상세 조회 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedDetailList(Map<String, Object> param);

	/**
	 * 급여 정보 저장
	 * @param param
	 * @return
	 */
	public void insertFeed(Map<String, Object> param);
	
	/**
	 * 급여 상세 저장
	 * @param param
	 * @return
	 */
	public void insertFeedDetail(Map<String, Object> param);

	/**
	 * 급여 정보 수정
	 * @param param
	 * @return
	 */
	public void updateFeed(Map<String, Object> param);

	/**
	 * 급여 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteFeed(Map<String, Object> param);

	/**
	 * 급여 상세 삭제
	 * @param param
	 * @return
	 */
	public void deleteFeedDetail(Map<String, Object> param);

}
