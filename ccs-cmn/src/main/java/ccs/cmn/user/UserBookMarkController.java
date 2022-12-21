package ccs.cmn.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.user.UserBookMarkService;
import ccs.framework.model.DataTableInfoVO;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;
import ccs.framework.util.PagingUtility;




@Controller
@RequestMapping(value = "/user/bookMark")
public class UserBookMarkController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserBookMarkController.class.getName());

	@Resource(name="UserBookMarkService")
	private UserBookMarkService userBookMarkService;
	
	/**
	 * 사용자 즐겨찾기 목록 페이지
	 * @param user_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{user_cd}")
	public String userBookMarkList(@PathVariable("user_cd") Long user_cd,
								Model model) throws Exception{
		model.addAttribute("user_cd", user_cd);
		return "cmn/user/userBookMarkList";
	}
	
	/**
	 * 사용자 고양이 즐겨찾기 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cat/selectData")
	public DataTableInfoVO selectCatData(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList = userBookMarkService.selectUserCatBookMarkList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}
	
	/**
	 * 사용자 서식지 즐겨찾기 목록  조회
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/habitat/selectData")
	public DataTableInfoVO selectHabitatData(@RequestBody Map<String, Object> param, SystemParameter systemParameter){
		Map<String,Object> data = HashMapUtility.<String,Object>create()
				.add(systemParameter.toMap())
				.add((Map<String,Object>)param.get("data"))
				.toMap();	
		DataTableInfoVO<Map<String, Object>> pageInfo = PagingUtility.<Map<String, Object>>create()
																.startPage(param);
		
		List<Map<String, Object>> dataList = userBookMarkService.selectUserHabitatBookMarkList(data);
		pageInfo.setPageInfo(dataList);
		
		return pageInfo;
	}

}
