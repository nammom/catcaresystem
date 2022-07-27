package ccs.cmn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;

import ccs.cmn.service.CmnService;
import ccs.cmn.service.FileService;
import ccs.cmn.service.SampleService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.impl.SampleServiceImpl;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

	private final Logger LOGGER = LoggerFactory.getLogger(MenuController.class.getName());
	
	@Resource(name="CmnService")
	private CmnService cmnService;
	
	@RequestMapping(value = "/etc")
	public String etc(@RequestParam("pagenm") String pagenm, Model model){
		model.addAttribute("pagenm", pagenm);
		return "cmn/menu/etc";
	}
	
	/**
	 * 메뉴 목록 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult selectMenuList() throws Exception{
		
		List<Map<String, Object>> menuList = cmnService.selectMenuList();
		Map<String,Object> result = new HashedMap();
		result.put("menuList", menuList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
	/**
	 * 모바일 etc 목록 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/etc/selectData")
	public AjaxResult selectEtcMenuList(JsonParameter jsonParameter) throws Exception{
		
		List<Map<String, Object>> etcMenuList = cmnService.selectEtcMenuList(jsonParameter.getData());
		Map<String,Object> result = new HashedMap();
		result.put("etcMenuList", etcMenuList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
		
		
}
