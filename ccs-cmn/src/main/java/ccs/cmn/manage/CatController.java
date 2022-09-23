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

import ccs.cmn.service.manage.CatService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/manage/cat")
public class CatController {

	private final Logger LOGGER = LoggerFactory.getLogger(CatController.class.getName());

	@Resource(name="CatService")
	private CatService catService;
	
	/**
	 * 고양이 목록 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping()
	public String catList(@RequestParam("pagenm") String pagenm, Model model){
		
		model.addAttribute("pagenm", pagenm);
		
		return "cmn/manage/catList";
	}
	
	/**
	 * 고양이 수정 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form/{cat_cd}")
	public String edit(@PathVariable("cat_cd") Long cat_cd, 
							Model model) throws Exception{
		
		model.addAttribute("cat_cd", cat_cd);
		
		return "cmn/manage/catForm";
	}
	
	/**
	 * 고양이 등록 페이지
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public String form(SystemParameter systemParameter, Model model) throws Exception{
		
		return "cmn/manage/catForm";
	}
	
	/**
	 * 고양이 검색 목록 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectData")
	public DataTableInfoVO selectHabitatList(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
				.startPage(param);

		List<Map<String, Object>> dataList = catService.selectCatList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	
	@ResponseBody
	@RequestMapping("/form/selectData")
	public AjaxResult selectCatDetail(JsonParameter jsonParameter){
		try {
			/*
			 * Map<String,Object> data = HashMapUtility.<String,Object>create()
			 * .add(jsonParameter.getData()) .toMap();
			 */
		
			Map<String, Object> detail = catService.selectCatDetail(jsonParameter.getData());
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, detail);
			
		} catch (Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 고양이 정보 저장(수정)
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
			
			Map<String,Object> result = catService.insertCat(param, fileParameter);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 고양이 정보 삭제
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
			catService.deleteCat(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, param);
			
		}catch(Exception e) {
			LOGGER.debug("delete error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	
}
