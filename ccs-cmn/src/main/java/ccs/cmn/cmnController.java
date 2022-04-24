package ccs.cmn;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ccs.cmn.service.SampleService;
import ccs.cmn.service.impl.SampleServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class cmnController {

	private final Logger LOGGER = LoggerFactory.getLogger(cmnController.class.getName());

	@Resource(name="SampleService")
	private SampleService sampleservice;
	
	@RequestMapping(value = "/test")
	public String cmn(){
		LOGGER.debug("/test 컨트롤러 진입");
/*		try {
			
		}catch(Exception e) {
			LOGGER.warn("msg", e);
		}
*/
		return "cmn/test";
	}
	
	@RequestMapping(value = "/home")
	public String cmnHome(){
//		sampleservice.selectSampleSql();
		return "cmn/home";
	}
	
	@RequestMapping(value = "/introduction")
	public String cmnIntroduction(){
		return "cmn/introduction";
	}
	
	@RequestMapping(value = "/login/login")
	public String cmnLogin(){
		return "cmn/login/login";
	}
	
	@RequestMapping(value = "/login/accessDenied")
	public String cmnLoginAccessDenied(){
		return "cmn/login/accessDenied";
	}
	
	@RequestMapping(value = "/admin/adminHome")
	public String cmnAdminHome(){
		return "cmn/admin/adminHome";
	}
}
