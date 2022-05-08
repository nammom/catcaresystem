package ccs.cmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.framework.model.AjaxResult;

@Controller
public class SampleController {

	private final Logger LOGGER = LoggerFactory.getLogger(SampleController.class.getName());

	@RequestMapping(value = "/sample/datatable")
	public String datatables(){
		
		return "sample/datatable";
	}
	
	@RequestMapping(value = "/sample/form")
	public String form(){
		
		return "sample/form";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/datatable/search")
	public Map<String, Object> search(@RequestBody Map<String, Object> param){
		
		param.get("name");//검색조건으로 사용
		System.out.println("name : " + param.get("name"));
		System.out.println("age : " + param.get("age"));
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> data1 = new HashMap<>();
		data1.put("name", "김지영");
		data1.put("age", 28);
		data1.put("city", "서울");
		dataList.add(data1);
		
		result.put("data", dataList); //data란 key로 결과를 넣어줘야함
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/form/save")
	public AjaxResult save(@RequestBody Map<String, Object> param){
		
		try {
			System.out.println("name : " + param.get("id"));
			System.out.println("age : " + param.get("password"));
			
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
