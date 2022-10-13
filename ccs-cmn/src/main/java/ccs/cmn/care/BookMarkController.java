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
import ccs.cmn.service.care.BookMarkService;
import ccs.cmn.service.care.CharacterService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.FileInfoVO;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;

@Controller
@RequestMapping(value = "/care/bookMark")
public class BookMarkController {

	private final Logger LOGGER = LoggerFactory.getLogger(BookMarkController.class.getName());

	@Resource(name="BookMarkService")
	private BookMarkService bookMarkService;
	
	 /** 고양이 즐겨찾기 목록 페이지
	 * @param target_type 	: "cat" or "grp" or "habitat"
	 * @param target_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{target_type}/{target_cd}")
	public String bookMarkList(@PathVariable("target_type") String target_type,
								@PathVariable("target_cd") Long target_cd , Model model) throws Exception{
		model.addAttribute("target_type", target_type);
		model.addAttribute("target_cd", target_cd);
		return "cmn/cat/bookMarkList";
	}
	
	/**
	 * 돌봄 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public DataTableInfoVO selectData(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList =  bookMarkService.selectBookMarkList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	/**
	 * 북마크 등록 or 해제
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxResult save(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();	
			Map<String, Object> data = bookMarkService.saveBookMark(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("bookmark error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	} 
}
