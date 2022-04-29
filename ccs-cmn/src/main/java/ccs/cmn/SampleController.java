package ccs.cmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.SampleService;
import ccs.cmn.service.impl.SampleServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
}
