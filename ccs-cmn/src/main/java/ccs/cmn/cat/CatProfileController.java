package ccs.cmn.cat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.cat.CatProfileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/cat/catProfile")
public class CatProfileController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatProfileController.class.getName());

	@Resource(name="CatProfileService")
	private CatProfileService catProfileService;
	
	/**
	 * 고양이 프로필 페이지
	 * @param target_type 	: "cat" or "grp"
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("{target_type}/{target_cd}")
	public String catProfile(@PathVariable("target_type") String target_type,
								@PathVariable("target_cd") Long target_cd,
								Model model) throws Exception{
		model.addAttribute("target_type", target_type);
		model.addAttribute("target_cd", target_cd);
		return "cmn/manage/catProfile";
	}
	
	/**
	 * 애칭 설정 모달 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/catName/form/{target_cd}")
	public String catName(@PathVariable("target_cd") Long target_cd , Model model) throws Exception{
		model.addAttribute("target_cd", target_cd);
		return "cmn/cat/catNameForm";
	}
	
	/**
	 * 고양이 프로필 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			Map<String, Object> data = catProfileService.selectCatProfile(param);
			if(!ObjectUtils.isEmpty(data)) {
				List<Map<String, Object>> disease = catProfileService.selectCatHealthy(param);
				data.put("disease", disease);
			}
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 고양이 애칭 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/catName/form/selectData")
	public AjaxResult selectCatName(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			Map<String, Object> data = catProfileService.selectCatName(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("selectCatName error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 *  고양이 애칭 등록
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/catName/form/save")
	public AjaxResult saveCatName(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			catProfileService.saveCatName(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.debug("saveCatName error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
}
