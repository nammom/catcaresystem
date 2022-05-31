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

import ccs.cmn.service.cat.CatGroupService;
import ccs.cmn.service.cat.CatProfileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/cat/catGroup")
public class CatGroupController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatGroupController.class.getName());

	@Resource(name="CatGroupService")
	private CatGroupService catGroupService;
	
	/**
	 * 고양이 무리 페이지
	 * @param catcd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{catcd}/{groupFlag}")
	public String catGroup(@PathVariable("catcd") Long catcd ,
							@PathVariable("groupFlag") String groupFlag ,
							Model model) throws Exception{
		model.addAttribute("cat_cd", catcd);
		model.addAttribute("groupFlag", groupFlag);
		return "cmn/cat/catGroup";
	}
	
	
	/**
	 * 돌봄 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCatGroupMemberList")
	public DataTableInfoVO selectCatGroupMemberList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  catGroupService.selectCatGroupMemberList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
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
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  catGroupService.selectCatGroupList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
}
