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
			//JspContext�� �� �±׸� ����� JSP �������� ���� ������ ����ִ�.
			PageContext context = (PageContext)this.getJspContext();
			
			//�±װ� ȣ��� JSP�� ���� ��û������ �����´�.
			HttpServletRequest request = (HttpServletRequest)context.getRequest();
			
			//Jsp �������� ����� �������� ����� ��� ��Ʈ���� ��´�.
			JspWriter out = context.getOut();
			
			String code = this.code;
			String prntCode = this.prntCode;
			String selectedValue = this.selectedValue;
			String includeAll = this.includeAll;
			
			//DB��ȸ
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
			
			//���
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
