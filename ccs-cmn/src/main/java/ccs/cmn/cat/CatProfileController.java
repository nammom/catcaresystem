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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.cat.CatProfileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/cat/catProfile")
public class CatProfileController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatProfileController.class.getName());

	@Resource(name="CatProfileService")
	private CatProfileService catProfileService;
	
	@RequestMapping("/{catcd}")
	public String catProfile(@PathVariable("catcd") Long catcd , Model model) throws Exception{
		model.addAttribute("cat_cd", catcd);
		return "cmn/cat/catProfile";
	}
	
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
	
	
}
