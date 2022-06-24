package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "FeedMapper")
public interface FeedMapper {

	/**
	 * ����� �޿� ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedList(Map<String, Object> param);
	
	/**
	 * ����� �޿� ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedCountList(Map<String, Object> param);
	
	/**
	 * ����� �޿� ���� ��ȸ - �ܰ�
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedByCd(Map<String, Object> param);

	/**
	 * ����� �޿� �� ��ȸ 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedDetailList(Map<String, Object> param);

	/**
	 * �޿� ���� ����
	 * @param param
	 * @return
	 */
	public void insertFeed(Map<String, Object> param);
	
	/**
	 * �޿� �� ����
	 * @param param
	 * @return
	 */
	public void insertFeedDetail(Map<String, Object> param);

	/**
	 * �޿� ���� ����
	 * @param param
	 * @return
	 */
	public void updateFeed(Map<String, Object> param);

	/**
	 * �޿� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteFeed(Map<String, Object> param);

	/**
	 * �޿� �� ����
	 * @param param
	 * @return
	 */
	public void deleteFeedDetail(Map<String, Object> param);

}
