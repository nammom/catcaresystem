package ccs.cmn.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.CmnService;
import ccs.cmn.service.manage.HabitatService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/manage/habitat")
public class HabitatController {

	private final Logger LOGGER = LoggerFactory.getLogger(HabitatController.class.getName());
	private static String PAGE_NAME = "habitat";
	
	@Resource(name="HabitatService")
	private HabitatService habitatService;
	
	/**
	 * 서식지 목록 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping()
	public String habitatList(Model model){
		
		model.addAttribute("pagenm", PAGE_NAME);
		
		return "cmn/manage/habitatList";
	}
	
	/**
	 * 서식지 프로필 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/profile/{habitat_cd}")
	public String profile(@PathVariable("habitat_cd") Long habitat_cd, 
							SystemParameter systemParameter,
							Model model) throws Exception{
		
		model.addAttribute("habitat_cd", habitat_cd);
		model.addAttribute("target_type", "habitat");
		
		return "cmn/manage/habitatProfile";
	}
	
	/**
	 * 서식지 수정 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form/{habitat_cd}")
	public String edit(@PathVariable("habitat_cd") Long habitat_cd, 
							SystemParameter systemParameter,
							Model model) throws Exception{
		
		model.addAttribute("habitat_cd", habitat_cd);
		
		return "cmn/manage/habitatForm";
	}
	
	/**
	 * 서식지 등록 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public String form(SystemParameter systemParameter, Model model) throws Exception{
		
		return "cmn/manage/habitatForm";
	}
	
	/**
	 * 서식지 검색 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public DataTableInfoVO selectHabitatList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
				.startPage(param);

		List<Map<String, Object>> dataList = habitatService.selectHabitatList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	/**
	 * 서식지 프로필 정보 조회
	 * @param jsonParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/selectData")
	public AjaxResult selectHabitatProfile(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> data = HashMapUtility.<String,Object>create()
					.add(systemParameter.toMap())
					.add(jsonParameter.getData())
					.toMap();
			
			Map<String, Object> habitatDetail = habitatService.selectHabitatProfile(data);
			habitatDetail.put("_SESSION_USER_CD_", systemParameter.toMap().get("_SESSION_USER_CD_"));
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, habitatDetail);

		}catch(Exception e) {
			LOGGER.debug("selectProfileData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 서식지 상세 정보 조회
	 * @param jsonParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> data = HashMapUtility.<String,Object>create()
					.add(systemParameter.toMap())
					.add(jsonParameter.getData())
					.toMap();
			
			Map<String, Object> habitatDetail = habitatService.selectHabitatDetail(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, habitatDetail);

		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 서식지 정보 저장(수정)
	 * @param jsonParameter
	 * @param systemParameter
	 * @param fileParameter
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
			
			Map<String,Object> result = habitatService.insertHabitat(param, fileParameter);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 서식지 정보 삭제
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
			habitatService.deleteHabitat(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, param);
			
		}catch(Exception e) {
			LOGGER.debug("delete error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	
}
