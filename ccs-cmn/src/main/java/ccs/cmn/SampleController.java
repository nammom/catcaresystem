package ccs.cmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@RequestMapping(value = "/sample/datatable")
	public String datatables(){
		
		return "sample/datatable";
	}
	
	@RequestMapping(value = "/sample/form")
	public String form(){
		
		return "sample/form";
	}
	
	@RequestMapping(value = "/sample/fileform")
	public String fileform(){
		
		return "sample/fileform";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/datatable/search")
	public Map<String, Object> search(@RequestBody Map<String, Object> param){
		
		param.get("name");//�˻��������� ���
		LOGGER.debug("name : " + param.get("name"));
		LOGGER.debug("age : " + param.get("age"));
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> data1 = new HashMap<>();
		data1.put("name", "������");
		data1.put("age", 28);
		data1.put("city", "����");
		dataList.add(data1);
		
		result.put("data", dataList); //data�� key�� ����� �־������
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/form/save")
	//public AjaxResult save(@RequestBody Map<String,Object> param){
	public AjaxResult save(JsonParameter jsonParameter){	
		try {
			Map<String,Object> param = jsonParameter.getData();
			LOGGER.debug("name : " + param.get("id"));
			LOGGER.debug("age : " + param.get("password"));
			
			//���� �� ��ȸ
			
			//����
			//service.save(param);
			
			//��ȸ
			//Map<String, Object> data1 = service.select(id);
			
			Map<String, Object> data1 = new HashMap<>();
			data1.put("id", "������");
			data1.put("password", 1234);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data1);
			
		}catch(Exception e) {
			return new AjaxResult(AjaxResult.STATUS.ERROR, "�����Ͽ����ϴ�.");
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/sample/fileform/save")
	public AjaxResult savefile(JsonParameter jsonParameter, SystemParameter systemParameter , FileParameter fileParameter){
		
		try {
			Map<String,Object> parameter = HashMapUtility.<String, Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			//----------------------------------------------------------- ���� ���� ������Ʈ ---------------------------------------------
			// ���ε��� ��������
			List<FileInfoVO> uploadedFiles = fileParameter.getFiles();
			// ������ �־����� ������ ���� ���� ���
			List<Map<String,Object>> filesToDelete = (List<Map<String,Object>>)parameter.get("deleteFiles");
			
			// ������ ���� �׷������� ������ ������Ʈ�� ���ؼ� �Ķ���ͷ� ���� ������ ����
			String FILE_GRP_ID = (String)parameter.get("FILE_GRP_ID");
			FILE_GRP_ID = uploadFileService.uploadFiles(systemParameter.toMap(), FILE_GRP_ID, uploadedFiles, filesToDelete);
			parameter.put("FILE_GRP_ID", FILE_GRP_ID);
			//----------------------------------------------------------- ���� ���� ������Ʈ ---------------------------------------------

			//������ ����
			//service.insertContents(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "�����Ͽ����ϴ�.");
		}
		
	}
	
}
