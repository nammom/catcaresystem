package ccs.cmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;

@Controller
public class FileUploadController {
	private final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class.getName());
	
	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@RequestMapping(value = "/sample/fileform")
	public String fileform(){
		
		return "sample/fileform";
	}
	
	@RequestMapping(value = "/sample/fileform/{formId}")
	public String updateFileform(@PathVariable("formId") String formId, Model model) throws Exception{

		model.addAttribute("formId", formId);
		return "sample/fileform";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sample/fileform/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter) {
		try {
			/*		
			Map<String, Object> param = jsonParameter.getData();
			Map<String, Object> form = service.selectForm(param.get("formId"));
			String file_grp_id = (String) form.get("file_grp_id");
			if(StringUtils.isNotEmpty(file_grp_id)) {
				List<Map<String, Object>> files = fileService.selectFiles(file_grp_id);
				model.addAttribute("files", files);
			}
			model.addAttribute("form", form);
			 */		
			
			Map<String, Object> data = new HashMap<>();
			List<String> color = new ArrayList<String>();
			color.add("blue");
			color.add("red");
			
			Map<String, Object> form = HashMapUtility.<String,Object>create()
					.add("title", "제목")
					.add("contents", "내용")
					.add("file_grp_id", 11L)
					.add("color", color)
					.add("regDt", "2022-11-18")
					.toMap();
			
			Long file_grp_id = (Long) form.get("file_grp_id");
			if(file_grp_id != null) {
				List<Map<String, Object>> files = fileService.selectFiles(file_grp_id);
				data.put("files", files);
			}
			data.put("form", form);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
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
			//----------------------------------------------------------- 파일 정보 업데이트 ---------------------------------------------
			
			// 업로드한 파일정보
			List<FileInfoVO> uploadedFiles = fileParameter.getFiles();
			// 기존에 있었지만 삭제된 파일 정보 목록
			List<Map<String,Object>> filesToDelete = (List<Map<String,Object>>)parameter.get("deleteFiles");
			
			// 기존에 파일 그룹정보가 있으면 업데이트를 위해서 파라미터로 정보 가지고 오기
			Long FILE_GRP_ID = null;
			if(StringUtils.isNotEmpty((String)parameter.get("file_grp_id"))) {
				FILE_GRP_ID = Long.parseLong((String)parameter.get("file_grp_id"));
			}
			FILE_GRP_ID = uploadFileService.uploadFiles(systemParameter.toMap(), FILE_GRP_ID, uploadedFiles, filesToDelete);
			parameter.put("file_grp_id", FILE_GRP_ID);
			
			//----------------------------------------------------------- 파일 정보 업데이트 ---------------------------------------------

			//폼정보 저장
			//String formId = service.insertContents(param);
			String formId = "1";
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("formId", formId);
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.warn("savefile error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
		
	}
}
