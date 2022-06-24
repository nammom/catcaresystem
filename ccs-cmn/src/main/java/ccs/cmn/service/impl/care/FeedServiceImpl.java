package ccs.cmn.service.impl.care;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import ccs.cmn.mapper.care.FeedMapper;
import ccs.cmn.service.care.FeedService;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.SessionUtility;


@Service(value="FeedService")
public class FeedServiceImpl implements FeedService {

	@Resource(name="FeedMapper")
	private FeedMapper feedMapper;

	private static String WATER_FEED_CD = "400";
	
	/**
	 * 고양이 급여 정보 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectFeedList(Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<>();
		
		//급여정보 조회
		List<Map<String, Object>> feedList = feedMapper.selectFeedList(param);
		List<Map<String, Object>> feedCountList = feedMapper.selectFeedCountList(param);
		
		//급여 일자로 묶기
		Map<String, List<Map<String, Object>>> feedMap = feedList.stream()
				.collect(Collectors.groupingBy(m -> (String)m.get("feed_dt")));
		
		Map<String, List<Map<String, Object>>> feedCountMap = feedCountList.stream()
				.collect(Collectors.groupingBy(m -> (String)m.get("feed_dt")));
		
		List<String> keys = feedMap.keySet().stream().collect(Collectors.toList());
		keys.stream().sorted(Comparator.reverseOrder())
					.forEach(key -> {
						Map<String, Object> map = HashMapUtility.<String, Object>create()
								.add("date", key)
								.add("feedList", feedMap.get(key))
								.add("feedCountMap", feedCountMap.get(key).get(0))
								.toMap();
						result.add(map);
					});
		
		return result;
	}
	
	/**
	 * 고양이 급여 상세 조회
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> selectFeedDetail(Map<String, Object> param) {
		
		//급여정보 조회
		List<Map<String, Object>> feedList = feedMapper.selectFeedByCd(param);
		if(!CollectionUtils.isEmpty(feedList)) {
			Map<String, Object> result = feedList.get(0);

			//급여 상세 조회
			List<Map<String, Object>> feedDetailList = feedMapper.selectFeedDetailList(param);		
			
			//feedDetailList feed_cd1로 분류
			Map<String, List<Map<String, Object>>> feedDetailMap = feedDetailList.stream()
					.collect(Collectors.groupingBy(detail -> (String)detail.get("feed_cd1")));
			
			result.put("feedDetailMap", feedDetailMap);

			return result;
		}else {
			return null;
		}
	}

	/**
	 * 급여 정보 저장(수정)
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertFeed(Map<String, Object> param) throws Exception{
		if(ObjectUtils.isEmpty(param.get("feed_cd"))){
			List<Map<String, Object>> catList = (List<Map<String, Object>>)param.get("catList");
			if(!CollectionUtils.isEmpty(catList)) {
				for(Map<String, Object> e : catList) {
					e.putAll(param);
					e.put("cat_cd", Integer.valueOf((String)e.get("cat_cd")));
					feedMapper.insertFeed(e);
					param.put("feed_cd", Integer.valueOf((String)e.get("feed_cd")));
					this.insertFeedDetail(param);
				}
			}
			
		}else {
			if(this.checkUserCd(param)) {
					feedMapper.updateFeed(param);
					feedMapper.deleteFeedDetail(param);
					this.insertFeedDetail(param);
			}else {
				throw new Exception();
			}
		}
	}

	/**
	 * 급여 상세 리스트 등록
	 * @param map
	 */
	private void insertFeedDetail(Map<String, Object> map) {
		List<Map<String, Object>> feedDetailList = (List<Map<String, Object>>)map.get("feedDetailList");
		if(!CollectionUtils.isEmpty(feedDetailList)) {
			for(Map<String, Object> e : feedDetailList) {
				feedMapper.insertFeedDetail(HashMapUtility.<String, Object>create()
														.add(map)
														.add(e)
														.toMap());
			}
		}
		if(StringUtils.equals((String)map.get("feed_cd1"), WATER_FEED_CD)) {
			feedMapper.insertFeedDetail(map);
		}
	}
	/**
	 * 급여 정보 삭제
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteFeed(Map<String, Object> param)  throws Exception{
		if(this.checkUserCd(param)) {
			feedMapper.deleteFeed(param);
			feedMapper.deleteFeedDetail(param);
		}else {
			throw new Exception();
		}
		
	}

	/**
	 * 게시글  작성자 여부 확인
	 * @param param
	 * @return
	 */
	private boolean checkUserCd(Map<String, Object> param) {
		List<Map<String, Object>> result = feedMapper.selectFeedByCd(param);
		if(CollectionUtils.isEmpty(result)) {
			return false;
		}
		UserDetailsDto userInfo = (UserDetailsDto)SessionUtility.getUserDetails();
		if(Long.compare((long) result.get(0).get("user_cd"), userInfo.getUserCd()) != 0 ) {
			return false;
		}
		
		return true;
	}


}
