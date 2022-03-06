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
		return "cmn/test";
	}
	
	@RequestMapping(value = "/home")
	public String cmnHome(){
		return "cmn/home";
	}
	
	@RequestMapping(value = "/introduction")
	public String cmnIntroduction(){
		return "cmn/introduction";
	}
	
	@RequestMapping(value = "/login")
	public String cmnLogin(){
		return "cmn/login/login";
	}
	
	@RequestMapping(value = "/login/accessDenied")
	public String cmnLoginAccessDenied(){
		return "cmn/login/accessDenied";
	}
	
	@RequestMapping(value = "/admin")
	public String cmnAdminHome(){
		return "cmn/admin/adminHome";
	}
}
