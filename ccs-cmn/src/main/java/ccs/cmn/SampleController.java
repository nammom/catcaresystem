package ccs.cmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import ccs.cmn.service.FileService;
import ccs.cmn.service.SampleService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;

@Controller
public class SampleController {

	private final Logger LOGGER = LoggerFactory.getLogger(SampleController.class.getName());

	@Resource(name="SampleService")
	private SampleService sampleservice;
	
	@RequestMapping(value = "/sample/datatable")
	public String datatables(){
		
		return "sample/datatable";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/datatable/search")
	public Map<String, Object> search(@RequestBody Map<String, Object> param){
		
		param.get("name");//검색조건으로 사용
		LOGGER.debug("name : " + param.get("name"));
		LOGGER.debug("age : " + param.get("age"));
		
		Page<Map<String, Object>> page = PageHelper.startPage((Integer)param.get("start")+1, (Integer)param.get("length"));
		List<Map<String, Object>> dataList =  sampleservice.selectDatatables(param);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("draw", (Integer)param.get("draw"));
		result.put("recordsTotal", (int)page.getTotal());
		result.put("recordsFiltered", (int)page.getTotal());
		result.put("data", dataList); //data란 key로 결과를 넣어줘야함
		
		return result;
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
