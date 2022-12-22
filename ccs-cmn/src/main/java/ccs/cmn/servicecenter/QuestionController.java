package ccs.cmn.servicecenter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.servicecenter.QuestionService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/servicecenter/question")
public class QuestionController {

	private final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class.getName());
	
	@Resource(name="QuestionService")
	private QuestionService questionService;

	/**
	 * 1:1 문의 등록 폼
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public String questionForm() throws Exception{
		
		return "cmn/servicecenter/questionForm";
	}
	
	/**
	 * 1:1 문의 목록 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping()
	public String question() {
		return "cmn/servicecenter/questionList";
	}
	
	/**
	 * 사용자 프로필 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping(value = "/selectData")
//	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
//		try {
//			Map<String,Object> param = HashMapUtility.<String,Object>create()
//										.add(jsonParameter.getData())
//										.add(systemParameter.toMap())
//										.toMap();	
//			Map<String, Object> data = userProfileService.selectUserProfile(param);
//			
//			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
//			
//		}catch(Exception e) {
//			LOGGER.debug("selectData error", e);
//			e.printStackTrace();
//			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
//		}
//	}

	/**
	 * 1:1 문의 목록 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) throws Exception{
		
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add(jsonParameter.getData())
				.toMap();
		
		//페이징 적용
		Map<String,Object> paging = (Map<String,Object>)data.get("paging");
		PagingUtility.<Map<String, Object>>create().startPaging(paging);
		
		List<Map<String, Object>> questionList = questionService.selectQuestionList(data);
		
		Map<String,Object> result = new HashedMap();
		result.put("questionList", questionList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
	/**
	 * 1:1 문의  정보 수정
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/save")
	public AjaxResult save(JsonParameter jsonParameter, SystemParameter systemParameter, FileParameter fileParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();
			
			questionService.saveQuestion(param, fileParameter);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
}
