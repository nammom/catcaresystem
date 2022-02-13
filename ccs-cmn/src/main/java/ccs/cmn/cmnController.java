package ccs.cmn;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ccs.cmn.service.SampleService;
import ccs.cmn.service.impl.SampleServiceImpl;

@Controller
public class cmnController {

	@Resource(name="SampleService")
	private SampleService sampleservice;
	
	@RequestMapping(value = "/test")
	public String cmn(){
		List<Map<String,Object>> result = sampleservice.selectSampleSql();
		System.out.println(result);
		
		return "cmn/test";
	}
}
