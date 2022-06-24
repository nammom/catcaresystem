package ccs.cmn;

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
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/search")
public class SearchController {

	private final Logger LOGGER = LoggerFactory.getLogger(SearchController.class.getName());

	@Resource(name="CmnService")
	private CmnService cmnService;
	
	/**
	 * 고양이 검색 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cat")
	public String searchCat(@RequestParam(required = false) String groupFlag,
							@RequestParam(required = false) Long target_cd,
							SystemParameter systemParameter,
							Model model) throws Exception{
		if(target_cd != null) {
			model.addAttribute("groupFlag", groupFlag);
		}
		if(target_cd != null) {
			model.addAttribute("target_cd", target_cd);
		}
		
		return "cmn/search/searchCatList";
	}

	/**
	 * 고양이 검색 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cat/selectSearchCatList")
	public DataTableInfoVO selectSearchCatList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		List<Map<String, Object>> dataList =  cmnService.selectSearchCatList(data);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
	/**
	 * 서식지 검색 페이지
	 * @param cat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/habitat")
	public String searchCat(@RequestParam(required = false) Long cat_cd,
							Model model) throws Exception{
		
		if(cat_cd != null) {
			model.addAttribute("cat_cd", cat_cd);
		}
		
		return "cmn/search/searchHabitatList";
	}
	
	/**
	 * 서식지 검색 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/habitat/selectSearchHabitatList")
	public DataTableInfoVO selectSearchHabitatList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		List<Map<String, Object>> dataList =  cmnService.selectSearchHabitatList(data);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
}
