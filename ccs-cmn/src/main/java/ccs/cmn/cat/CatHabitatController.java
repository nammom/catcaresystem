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
import ccs.cmn.service.cat.CatHabitatService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/cat/catHabitat")
public class CatHabitatController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatHabitatController.class.getName());

	@Resource(name="CatHabitatService")
	private CatHabitatService catHabitatService;
	
	/**
	 * 고양이 서식지 페이지
	 * @param cat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{cat_cd}")
	public String catHabitat(@PathVariable("cat_cd") Long cat_cd,
							Model model) throws Exception{
		model.addAttribute("cat_cd", cat_cd);
		return "cmn/cat/catHabitat";
	}

	/**
	 * 고양이 서식지 정보 조회 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCatHabitatList")
	public DataTableInfoVO selectCatHabitatList(@RequestBody Map<String, Object> param){
		List<Map<String, Object>> dataList =  catHabitatService.selectCatHabitatList(param);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
	/**
	 * 고양이 서식지 등록
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult insertCatHabitat(JsonParameter jsonParameter, SystemParameter systemParameter){
		try {
			Map<String,Object> data = HashMapUtility.<String, Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			
			catHabitatService.insertCatHabitat(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.warn("insertCatHabitat error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 고양이 서식지 삭제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxResult deleteCatHabitat(JsonParameter jsonParameter, SystemParameter systemParameter){
		try {
			Map<String,Object> data = HashMapUtility.<String, Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			
			catHabitatService.deleteCatHabitat(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.warn("deleteCatHabitat error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}

}
