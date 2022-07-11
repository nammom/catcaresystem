package ccs.cmn.care;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.CmnService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.care.CharacterService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;

@Controller
@RequestMapping(value = "/care/character")
public class CharacterController {

	private final Logger LOGGER = LoggerFactory.getLogger(CharacterController.class.getName());

	@Resource(name="CharacterService")
	private CharacterService characterService;
	
	@Resource(name="CmnService")
	private CmnService cmnService;
	
	/**
	 * 고양이 특징 정보 목록 페이지
	 * @param target_type 	: "cat" or "grp" or "habitat"
	 * @param target_cd		: cat_cd or cat_grp_cd or habitat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{target_type}/{target_cd}")
	public String characterList(@PathVariable("target_type") String target_type,
					   @PathVariable("target_cd") Long target_cd,
					   Model model) throws Exception{
		
		model.addAttribute("target_type", target_type);
		model.addAttribute("target_cd", target_cd);
		
		return "cmn/care/characterList";
	}
	
	/**
	 * 고양이 특징 정보 상세 페이지
	 * @param character_cd
	 * @param systemParameter
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{character_cd}")
	public String feedDetail(@PathVariable("character_cd") Long character_cd,
								SystemParameter systemParameter,
								Model model) throws Exception{
		
		model.addAttribute("character_cd", character_cd);
		model.addAllAttributes(systemParameter.toMap());
		
		return "cmn/care/characterForm";
	}
	
	/**
	 * 고양이 특징 정보 등록 페이지
	 * @param cat_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form/{cat_cd}")
	public String feedForm(@PathVariable("cat_cd") Long cat_cd,
							SystemParameter systemParameter,
							Model model) throws Exception{
		
		model.addAttribute("cat_cd", cat_cd);
		model.addAllAttributes(systemParameter.toMap());
		
		return "cmn/care/characterForm";
	}
	
	/**
	 * 고양이 특징 정보 목록 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult characterInfo(JsonParameter jsonParameter, SystemParameter systemParameter) throws Exception{
		
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add(jsonParameter.getData())
				.toMap();
		
		//페이징 적용
		Map<String,Object> paging = (Map<String,Object>)data.get("paging");
		PagingUtility.<Map<String, Object>>create().startPaging(paging);
		
		List<Map<String, Object>> characterList = characterService.selectCharacterList(data);
		
		Map<String,Object> result = new HashedMap();
		result.put("characterList", characterList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
	/**
	 * 고양이 특징 정보 조회
	 * @param jsonParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> data = HashMapUtility.<String,Object>create()
					.add(systemParameter.toMap())
					.add(jsonParameter.getData())
					.toMap();
			
			Map<String, Object> characterDetail = characterService.selectCharacterDetail(data);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, characterDetail);

		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 특징 정보 저장(수정)
	 * @param jsonParameter
	 * @param systemParameter
	 * @param fileParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/save")
	public AjaxResult save(JsonParameter jsonParameter, SystemParameter systemParameter, FileParameter fileParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			
			characterService.insertCharacter(param, fileParameter);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 특징 정보 삭제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/delete")
	public AjaxResult delete(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			characterService.deleteCharacter(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, param);
			
		}catch(Exception e) {
			LOGGER.debug("delete error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
}
