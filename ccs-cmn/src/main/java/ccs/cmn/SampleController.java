package ccs.cmn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import ccs.cmn.service.SampleService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.util.PagingUtility;

@Controller
public class SampleController {

	private final Logger LOGGER = LoggerFactory.getLogger(SampleController.class.getName());

	@Resource(name="SampleService")
	private SampleService sampleservice;
	
	@RequestMapping(value = "/sample/datatable")
	public String datatables(){
		
		return "sample/datatable";
	}
	
	/**
	 * datatables 페이징 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sample/datatable/searchPaging")
	public DataTableInfoVO searchPaging(@RequestBody Map<String, Object> param){
		
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  sampleservice.selectDatatables(param);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	
	/**
	 * datatables 페이징 X 조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sample/datatable/search")
	public DataTableInfoVO search(@RequestBody Map<String, Object> param){
		
		List<Map<String, Object>> dataList =  sampleservice.selectDatatables(param);
		
		DataTableInfoVO<Map<String,Object>> dataInfo = new DataTableInfoVO<>(dataList);
		
		return dataInfo;
	}
	
	
	@RequestMapping(value = "/sample/form")
	public String form(){
		
		return "sample/form";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/form/save")
	//public AjaxResult save(@RequestBody Map<String,Object> param){
	public AjaxResult save(JsonParameter jsonParameter){	
		try {
			Map<String,Object> param = jsonParameter.getData();
			LOGGER.debug("name : " + param.get("id"));
			LOGGER.debug("age : " + param.get("password"));
			
			//저장 후 조회
			
			//저장
			//service.save(param);
			
			//조회
			//Map<String, Object> data1 = service.select(id);
			
			Map<String, Object> data1 = new HashMap<>();
			data1.put("id", "김지영");
			data1.put("password", 1234);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data1);
			
		}catch(Exception e) {
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
		
	}
	
	
	
}
