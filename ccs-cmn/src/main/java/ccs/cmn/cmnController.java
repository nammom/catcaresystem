package ccs.cmn;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;

import ccs.cmn.service.FileService;
import ccs.cmn.service.SampleService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.impl.SampleServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class cmnController {

	private final Logger LOGGER = LoggerFactory.getLogger(cmnController.class.getName());

	@Resource(name="SampleService")
	private SampleService sampleservice;
	
	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@RequestMapping(value = "/test")
	public String cmn(){
		PageHelper.startPage(2, 1);
		List<Map<String,Object>> map = sampleservice.selectSampleSql();
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
	
	@GetMapping(value = "/downloadfile")
	public void downloadFile(@RequestParam("fileId") Long fileId, HttpServletRequest request, HttpServletResponse response){
		try {
			uploadFileService.downloadFile(fileId, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
