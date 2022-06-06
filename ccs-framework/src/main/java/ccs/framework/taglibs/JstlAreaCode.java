package ccs.framework.taglibs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import ccs.framework.dataservice.ServiceCall;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class JstlAreaCode extends SimpleTagSupport{
	
	private String code;
	private String prntCode;
	private String selectedValue;
	private String includeAll;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		try {
			//JspContext는 이 태그를 사용한 JSP 페이지에 대한 정보를 담고있다.
			PageContext context = (PageContext)this.getJspContext();
			
			//태그가 호출된 JSP에 대해 요청정보를 가져온다.
			HttpServletRequest request = (HttpServletRequest)context.getRequest();
			
			//Jsp 페이지의 결과를 브라우저로 출력할 출력 스트림을 얻는다.
			JspWriter out = context.getOut();
			
			String code = this.code;
			String prntCode = this.prntCode;
			String selectedValue = this.selectedValue;
			String includeAll = this.includeAll;
			
			//DB조회
			String serviceName,beanName;
			serviceName = "selectAreaCodeList";
			beanName = "CmnService";
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("code", code);
			if(StringUtils.isNotEmpty(prntCode)) {
				parameters.put("prntCode", prntCode);
			}
			ServiceCall service = new ServiceCall(beanName, serviceName);
			service.callMethod(parameters);
			List<Map<String, Object>> codeList = (List<Map<String, Object>>)service.getData();
			
			//출력
			out.print(this.getOptionHtml(codeList));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private String getOptionHtml(List<Map<String, Object>> codeList) {
		String resultHtml = "";
		if(StringUtils.isNotEmpty(this.includeAll)) {
			resultHtml = "<option value=\"\">" + this.includeAll + "</option>";
		}
		for(Map<String, Object> e : codeList) {
			resultHtml += String.format("<option value='%s' %s %s>%s</option>", 
							(String)e.get("value"),
							this.selectedValue != null && this.selectedValue.equals((String)e.get("value"))? "selected" : "",
							e.get("prntcode") != null? "data-prnt=\"" + (String)e.get("prntcode")+ "\"" : "",		
							(String)e.get("name"));
		}
		
		return resultHtml;
	}
	
}
