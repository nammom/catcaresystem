package ccs.cmn.servicecenter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.servicecenter.NoticeService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.JsonParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/servicecenter/notice")
public class NoticeController {

	private final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class.getName());
	
	@Resource(name="NoticeService")
	private NoticeService noticeService;
	
	/**
	 * 공지사항 목록 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping()
	public String question() {
		return "cmn/servicecenter/noticeList";
	}
	
	/**
	 * 1:1 문의 목록 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter) throws Exception{
		
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(jsonParameter.getData())
				.toMap();
		
		//페이징 적용
		Map<String,Object> paging = (Map<String,Object>)data.get("paging");
		PagingUtility.<Map<String, Object>>create().startPaging(paging);
		
		List<Map<String, Object>> noticeList = noticeService.selectNoticeList(data);
		
		Map<String,Object> result = new HashedMap();
		result.put("noticeList", noticeList);
		
		return new AjaxResult(AjaxResult.STATUS.SUCCESS, result);
	}
	
}
