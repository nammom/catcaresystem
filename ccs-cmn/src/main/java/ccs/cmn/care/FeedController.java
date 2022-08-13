package ccs.cmn.care;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;

import ccs.cmn.service.CmnService;
import ccs.cmn.service.care.FeedService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/care/feed")
public class FeedController {

	private final Logger LOGGER = LoggerFactory.getLogger(FeedController.class.getName());

	@Resource(name="FeedService")
	private FeedService feedService;
	
	@Resource(name="CmnService")
	private CmnService cmnService;
	
	/**
	 * 고양이 급여 정보 목록 페이지
	 * @param target_type 	: "cat" or "grp" or "habitat"
	 * @param target_cd		: cat_cd or cat_grp_cd or habitat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{target_type}/{target_cd}")
	public String feedList(@PathVariable("target_type") String target_type,
					   @PathVariable("target_cd") Long target_cd,
					   Model model) throws Exception{
		
		model.addAttribute("target_type", target_type);
		model.addAttribute("target_cd", target_cd);
		
		return "cmn/care/feedList";
	}
	
	/**
	 * 고양이 급여 정보 달력 페이지
	 * @param target_type 	: "cat" or "grp" or "habitat"
	 * @param target_cd		: cat_cd or cat_grp_cd or habitat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calendar/{target_type}/{target_cd}")
	public String feedCalendar(@PathVariable("target_type") String target_type,
					   @PathVariable("target_cd") Long target_cd,
					   Model model) throws Exception{
		
		model.addAttribute("target_type", target_type);
		model.addAttribute("target_cd", target_cd);
		
		return "cmn/care/feedCalendar";
	}
	
	
	/**
	 * 고양이 급여 정보 상세 페이지
	 * @param feed_cd
	 * @param target_type 	: "cat" or "grp" or "habitat"
	 * @param target_cd		: cat_cd or cat_grp_cd or habitat_cd
	 * @param systemParameter
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{feed_cd}")
	public String feedDetail(@PathVariable("feed_cd") Long feed_cd,
								SystemParameter systemParameter,
								Model model) throws Exception{
		
		model.addAttribute("feed_cd", feed_cd);
		model.addAttribute("_SESSION_USER_CD_", systemParameter.toMap().get("_SESSION_USER_CD_"));
		
		return "cmn/care/feedForm";
	}
	
	/**
	 * 고양이 급여 정보 등록 페이지
	 * @param cat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form/{cat_cd}")
	public String feedForm(@PathVariable("cat_cd") Long cat_cd,
							SystemParameter systemParameter,
							Model model) throws Exception{
		
		model.addAttribute("cat_cd", cat_cd);
		model.addAttribute("_SESSION_USER_CD_", systemParameter.toMap().get("_SESSION_USER_CD_"));
		
		return "cmn/care/feedForm";
	}
	
	/**
	 * 고양이 급여 정보 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult feedList(JsonParameter jsonParameter, SystemParameter systemParameter){
		
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add(jsonParameter.getData())
				.toMap();
		
		List<Map<String, Object>> feedList = feedService.selectFeedMapList(data);
		
		Map<String,Object> result = new HashedMap();
		result.put("feedList", feedList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
	/**
	 * 고양이 급여 정보 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/calendar/selectData")
	public AjaxResult calendarFeedList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add(param)
				.toMap();
		
		List<Map<String, Object>> feedList = feedService.selectFeedList(data);
		
		Map<String,Object> result = new HashedMap();
		result.put("feedList", feedList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
	/**
	 * 고양이 돌봄 정보 조회
	 * @param jsonParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> result = new HashedMap();
			List<Map<String, Object>> feedCd2CodeList = cmnService.selectCmnCodeList(HashMapUtility.<String,Object>create()
										.add("code", "C0004").toMap());
			//feed_cd2로 분류
			Map<String, List<Map<String, Object>>> feedCd2CodeMap = feedCd2CodeList.stream()
				.filter(m -> !ObjectUtils.isEmpty(m.get("prntcode")))
				.collect(Collectors.groupingBy(m -> (String)m.get("prntcode")));	
			
			result.put("feedCd2CodeMap", feedCd2CodeMap);
			
			Map<String,Object> data = HashMapUtility.<String,Object>create()
					.add(systemParameter.toMap())
					.add(jsonParameter.getData())
					.toMap();
			
			if(jsonParameter.getData().containsKey("feed_cd")) {
				Map<String, Object> feedDetail = feedService.selectFeedDetail(data);
				result.put("feedDetail", feedDetail);
			}else {
				List<Map<String, Object>> catList = cmnService.selectSearchCatList(data);
				result.put("catList", catList);
			}
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);

		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 돌봄 정보 저장(수정)
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/save")
	public AjaxResult save(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			feedService.insertFeed(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 돌봄 정보 삭제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/delete")
	public AjaxResult delete(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			feedService.deleteFeed(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, param);
			
		}catch(Exception e) {
			LOGGER.debug("delete error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
}
