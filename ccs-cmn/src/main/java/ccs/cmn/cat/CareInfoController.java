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

import ccs.cmn.service.cat.CareInfoService;
import ccs.cmn.service.cat.CatProfileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/cat/careInfo")
public class CareInfoController {

	private final Logger LOGGER = LoggerFactory.getLogger(CareInfoController.class.getName());

	@Resource(name="CareInfoService")
	private CareInfoService careInfoService;
	
	/**
	 * 고양이 돌봄 정보 페이지
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{target_cd}")
	public String careInfo(@PathVariable("target_cd") Long target_cd , Model model) throws Exception{
		model.addAttribute("cat_cd", target_cd);
		return "cmn/cat/careInfo";
	}
	

	@RequestMapping("/detail/{catcare_cd}")
	public String careInfoDetail(@PathVariable("catcare_cd") Long catcare_cd,
								SystemParameter systemParameter,
								Model model) throws Exception{
		model.addAttribute("catcare_cd", catcare_cd);
		
		return "cmn/cat/careInfoForm";
	}
	
	@RequestMapping("/form/{cat_cd}")
	public String careInfoForm(@PathVariable("cat_cd") Long cat_cd,
								SystemParameter systemParameter,
								Model model) throws Exception{
		
		model.addAttribute("cat_cd", cat_cd);
		
		return "cmn/cat/careInfoForm";
	}
	
	/**
	 * 돌봄 정보 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public DataTableInfoVO careList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList = careInfoService.selectCareInfoList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	/**
	 * 고양이 돌봄 정보 조회
	 * @param jsonParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter) {
		try {
			List<Map<String, Object>> dataList = careInfoService.selectCareInfoList(jsonParameter.getData());
			if(!CollectionUtils.isEmpty(dataList)) {
				return new AjaxResult(AjaxResult.STATUS.SUCCESS, dataList.get(0));
			}
			throw new Exception();
		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 돌봄 정보 저장(수정)
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/save")
	public AjaxResult save(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			Map<String, Object> data = careInfoService.insertCareInfo(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 돌봄 정보 삭제
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
			careInfoService.deleteCareInfo(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, param);
			
		}catch(Exception e) {
			LOGGER.debug("delete error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
}
