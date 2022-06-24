package ccs.cmn.service.care;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface FeedService {

	/**
	 * ����� �޿� ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectFeedList(Map<String, Object> param);

	/**
	 * ����� �޿� �� ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectFeedDetail(Map<String, Object> param);

	/**
	 * �޿� ���� ����(����)
	 * @param param
	 * @return
	 */
	public void insertFeed(Map<String, Object> param)  throws Exception;

	/**
	 * �޿� ���� ����
	 * @param param
	 * @return
	 */
	public void deleteFeed(Map<String, Object> param)  throws Exception;


}
