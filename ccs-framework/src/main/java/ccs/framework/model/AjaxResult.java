package ccs.framework.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class AjaxResult {
	@Getter
	private String status;
	@Getter@Setter
	private Map<String, Object> data;
	
	public AjaxResult(STATUS status){
		setStatus(status);
	}
	
	public AjaxResult(STATUS status, Map<String, Object> data){
		setStatus(status);
		this.data = data;
	}
	
	public AjaxResult(STATUS status, String msg){
		setStatus(status);
		Map<String, Object> msgData = new HashMap<String, Object>();
		msgData.put("msg", msg);
		this.data = msgData;
	}

	public static enum STATUS {
		SUCCESS("101"),
		WARN("102"),
		ERROR("103");

		public String code;
		
		public String getCode() {
			return code;
		}
		
		STATUS(String code) {
			this.code = code;// TODO Auto-generated constructor stub
		}
	}
	
	public void setStatus(STATUS status) {
		this.status = status.getCode();
	}

	
	
}
