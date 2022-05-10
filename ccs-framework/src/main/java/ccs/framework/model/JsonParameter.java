package ccs.framework.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ccs.framework.dataservice.BeanService;
import lombok.Getter;
import lombok.Setter;


public class JsonParameter {
	
	private static final Logger logger = LogManager.getLogger(JsonParameter.class);

	@Getter@Setter
	private Map<String, Object> data = new HashMap<String, Object>();
	private static String DATA_KEY = "___AJAX_DATA___";
	
	public JsonParameter(){
		this(null);
	}
	
	public JsonParameter(Map<String, Object> data){
		this.setData(data);
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public void setData(Map<String, Object> data) {
		if(data == null){
			this.data = new HashMap<String, Object>();
		} else{
			this.data = data;
		}
	}
	
	public static JsonParameter create(final HttpServletRequest request) throws Exception{
		JsonParameter jsonParameter = new JsonParameter();
		Map<String, Object> bodyMap = jsonParameter.getData();
		String jsonBody;
		if(request instanceof MultipartHttpServletRequest){
			jsonBody =(String)request.getParameter(DATA_KEY);
			jsonParameter.setData((Map<String, Object>)getData(jsonBody));
		}else {
			jsonBody = IOUtils.toString(request.getInputStream());
			if(StringUtils.isNotEmpty(jsonBody)) {
				jsonParameter.setData((Map<String, Object>)getData(jsonBody).get(DATA_KEY));
			}
		}
		return jsonParameter;
	}
	
	public static Map<String,Object> getData(String jsonString) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(df);
		Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
		
		return (Map<String, Object>)map;
	}
}