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
	 * @param catcd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{catcd}")
	public String catProfile(@PathVariable("catcd") Long catcd , Model model) throws Exception{
		model.addAttribute("cat_cd", catcd);
		return "cmn/cat/catProfile";
	}
	
	/**
	 * 고양이 돌봄 목록 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/careList/{target_cd}")
	public String careList(@PathVariable("target_cd") Long target_cd , Model model) throws Exception{
		model.addAttribute("target_cd", target_cd);
		return "cmn/cat/careList";
	}
	
	/**
	 * 고양이 즐겨찾기 목록 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bookMarkList/{target_cd}")
	public String bookMarkList(@PathVariable("target_cd") Long target_cd , Model model) throws Exception{
		model.addAttribute("target_cd", target_cd);
		return "cmn/cat/bookMarkList";
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
	 *  돌봄 등록 or 해제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/care")
	public AjaxResult care(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			Map<String, Object> data = catProfileService.saveCare(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("care error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 북마크 등록 or 해제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bookmark")
	public AjaxResult bookmark(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			Map<String, Object> data = catProfileService.saveBookMark(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("bookmark error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 돌봄 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/careList/selectData")
	public DataTableInfoVO careList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  catProfileService.selectCareList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	/**
	 * 돌봄 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bookMarkList/selectData")
	public DataTableInfoVO bookMarkList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  catProfileService.selectCareList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
}
