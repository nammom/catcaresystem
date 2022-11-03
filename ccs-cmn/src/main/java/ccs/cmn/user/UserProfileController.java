package ccs.cmn.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccs.cmn.service.user.UserProfileService;
import ccs.framework.model.AjaxResult;
import ccs.framework.model.FileParameter;
import ccs.framework.model.JsonParameter;
import ccs.framework.model.SystemParameter;
import ccs.framework.util.HashMapUtility;




@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class.getName());

	@Resource(name="UserProfileService")
	private UserProfileService userProfileService;
	
	/**
	 * 사용자 프로필 페이지
	 * @param user_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{user_cd}")
	public String catProfile(@PathVariable("user_cd") Long user_cd,
								Model model) throws Exception{
		model.addAttribute("user_cd", user_cd);
		return "cmn/user/userProfile";
	}
	
	/**
	 * 사용자 프로필 수정 
	 * @param user_cd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form/{user_cd}")
	public String form(@PathVariable("user_cd") Long user_cd,
								Model model) throws Exception{
		model.addAttribute("user_cd", user_cd);
		return "cmn/user/userProfileForm";
	}
	
	/**
	 * 사용자 프로필 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectData")
	public AjaxResult selectData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			Map<String, Object> data = userProfileService.selectUserProfile(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("selectData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}

	/**
	 * 사용자 프로필 조회
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/selectData")
	public AjaxResult selectProfileData(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String,Object> param = HashMapUtility.<String,Object>create()
										.add(jsonParameter.getData())
										.add(systemParameter.toMap())
										.toMap();	
			Map<String, Object> data = userProfileService.selectUserProfileDetail(param);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("selectProfileData error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 사용자 프로필 정보 수정
	 * @param jsonParameter
	 * @param systemParameter
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
			
			userProfileService.saveUserProfile(param, fileParameter);
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS);
			
		}catch(Exception e) {
			LOGGER.debug("save error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
	
	/**
	 * 사용자 닉네임 중복 체크
	 * @param jsonParameter
	 * @param systemParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/form/checkNickname")
	public AjaxResult checkNickname(JsonParameter jsonParameter, SystemParameter systemParameter) {
		try {
			Map<String, Object> data = new HashMap<>();
			Map<String,Object> param = HashMapUtility.<String,Object>create()
					.add(jsonParameter.getData())
					.add(systemParameter.toMap())
					.toMap();
			if(userProfileService.checkNickname(param)) {
				data.put("status", "Y");
			} else {
				data.put("status", "N");
			}
			
			return new AjaxResult(AjaxResult.STATUS.SUCCESS, data);
			
		}catch(Exception e) {
			LOGGER.debug("checkNickname error", e);
			e.printStackTrace();
			return new AjaxResult(AjaxResult.STATUS.ERROR, "실패하였습니다.");
		}
	}
}
