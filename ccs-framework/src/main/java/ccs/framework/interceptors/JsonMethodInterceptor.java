package ccs.framework.interceptors;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ccs.framework.dataservice.BeanService;
import ccs.framework.dataservice.ServiceCall;
import ccs.framework.dataservice.UUIDService;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;
import ccs.framework.util.StringUtility;





public class JsonMethodInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonMethodInterceptor.class);
	
	//@Resource(name = "config")
    protected Properties config = null;

	private void wirteLog(String msg){
		if(logger.isDebugEnabled()){
			logger.debug(msg);
		}
	}
	
	private void connectConfig() {
		if(config == null) {
			config = BeanService.getBeanService("config", Properties.class);
		}
	}
	

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		connectConfig();
		wirteLog("================================================================== START ============================================");
		printRequestInfo(request);
		try
		{
			/*
			 * wirteLog(config.getProperty("Globals.Login.auto.YN"));
			 * System.out.println(config.getProperty("Globals.Login.auto.YN"));
			 * System.out.println(SessionUtility.getUserDetails());
			 * if(StringUtility.isEqual(config.getProperty("Globals.Login.auto.YN"), "Y") &&
			 * SessionUtility.getUserInfo() == null){
			 * wirteLog("================================================================== START  ============================================"
			 * ); wirteLog("AUTO LOGIN START ====================================="); String
			 * beanName = config.getProperty("Globals.Login.auto.BeanName"); String
			 * beanMethodName = config.getProperty("Globals.Login.auto.BeanMethodName");
			 * String userId = config.getProperty("Globals.Login.auto.Parameter.Id"); String
			 * userPwd = config.getProperty("Globals.Login.auto.Parameter.Pwd");
			 * 
			 * 
			 * ServiceCall callService = new ServiceCall(beanName,beanMethodName);
			 * callService.callMethod(userId,userPwd,"auto");
			 * wirteLog("AUTO LOGIN END ====================================="); }
			 * 
			 * 
			 * boolean rtnValue = new
			 * JsonResultUtility(request,response,handler).parse(getCurrentIntercept());
			 * wirteLog("================================================================== END ============================================"
			 * );
			 * 
			 * if(rtnValue == false){
			 * 
			 * } else{ // true�씪 寃쎌슦 異붽��쟻�쑝濡� controller . request url.
			 * wirteLog("================================================================== Controller Start ============================================"
			 * ); }
			 */
			return true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
		/**
		 * 硫붾돱 �젙蹂닿� �엳�쓣 寃쎌슦 硫붾뒌[�뀛 ���븳 �젙蹂대�� �룷�븿�떆�궓�떎.
		 */
		if (modelAndView != null) {
			UserDetailsDto userInfo = (UserDetailsDto)SessionUtility.getUserDetails();
			if(!ObjectUtils.isEmpty(userInfo)) {
				modelAndView.getModelMap().addAttribute("_SESSION_USER_CD_",userInfo.getUserCd());
			}
		}
	} 
	
	
	private void printRequestInfo(HttpServletRequest req) {
	    StringBuffer requestURL = req.getRequestURL();
	    String queryString = req.getQueryString();

	    if (queryString == null) {
	    	wirteLog("url: " + requestURL.toString());
	    } else {
	    	wirteLog("url: " + requestURL.append('?').append(queryString).toString());
	    }

	    wirteLog( "form method:" + req.getMethod());

	    // print all the headers
	    Enumeration headerNames = req.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	        String headerName = (String)headerNames.nextElement();
	        wirteLog("header: " + headerName + ":" + req.getHeader(headerName));
	    }

	    // print all the request params
	    Enumeration params = req.getParameterNames();
	    while(params.hasMoreElements()){
	        String paramName = (String)params.nextElement();
	        wirteLog("Parameter: "+paramName+"= "+req.getParameter(paramName) + "");
	    }
	}

	
	/**
	 * View �옉�뾽源뚯� �셿猷뚮맂 �썑 �샇異�. responseBody 瑜� �씠�슜�븷 寃쎌슦 UI �뿉 �씠誘� 媛믪쓣 �쟾�떖�썑 �빐�떦 遺�遺꾩씠 �샇異�
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		wirteLog("================================================================== END ============================================");
	}
	
}