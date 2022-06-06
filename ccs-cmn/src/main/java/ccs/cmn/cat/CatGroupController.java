package ccs.cmn.cat;

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
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.CmnService;
import ccs.cmn.service.cat.CatGroupService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/cat/catGroup")
public class CatGroupController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatGroupController.class.getName());

	@Resource(name="CatGroupService")
	private CatGroupService catGroupService;

	@Resource(name="CmnService")
	private CmnService cmnService;
	
	/**
	 * 고양이 무리 페이지
	 * @param catcd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{target_cd}/{groupFlag}")
	public String catGroup(@PathVariable("target_cd") Long target_cd ,
							@PathVariable("groupFlag") String groupFlag ,
							Model model) throws Exception{
		model.addAttribute("target_cd", target_cd);
		model.addAttribute("groupFlag", groupFlag);
		return "cmn/cat/catGroup";
	}
	
	/**
	 * 고양이 검색 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchCat/{target_cd}/{groupFlag}")
	public String searchCat(@PathVariable("target_cd") Long target_cd,
							@PathVariable("groupFlag") String groupFlag,
							SystemParameter systemParameter,
							Model model) throws Exception{
		
		Map<String, Object> catArea = cmnService.selectCatArea(target_cd);
		model.addAttribute("catArea", catArea);
		model.addAttribute("target_cd", target_cd);
		model.addAttribute("groupFlag", groupFlag);
		
		return "cmn/cat/searchCatList";
	}

	/**
	 * 돌봄 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCatGroupList")
	public DataTableInfoVO selectCatGroupList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		List<Map<String, Object>> dataList =  catGroupService.selectCatGroupList(data);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
	/**
	 * 고양이 그룹 등록
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult insertCatGroup(JsonParameter jsonParameter, SystemParameter systemParameter){
		try {
			Map<String,Object> data = HashMapUtility.<String, Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			
			catGroupService.insertCatGroup(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.warn("insertCatGroup error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 고양이 그룹 삭제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult deleteCatGroup(JsonParameter jsonParameter, SystemParameter systemParameter){
		try {
			Map<String,Object> data = HashMapUtility.<String, Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			
			catGroupService.deleteCatGroup(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.warn("deleteCatGroup error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}

	/**
	 * 고양이 검색 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSearchCatList")
	public DataTableInfoVO selectSearchCatList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		List<Map<String, Object>> dataList =  cmnService.selectSearchCatList(data);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
}
