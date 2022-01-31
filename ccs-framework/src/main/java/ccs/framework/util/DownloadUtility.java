package ccs.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadUtility {

	private HttpServletResponse response;
	
	private String contentType;
	
	private String fileName;
	
	private String fileExtension;
	
	
	private List<byte[]> bytes;
	
	private List<String> files;
	
	private List<String> strings;
	
	public DownloadUtility(HttpServletResponse response){
		this.response = response;
		this.bytes = new ArrayList<byte[]>();
		this.files = new ArrayList<String>();
		this.strings = new ArrayList<String>();
	}
	
	public static DownloadUtility create(HttpServletResponse response){
		return new DownloadUtility(response);
	}

	public String getContentType() {
		return contentType;
	}

	public DownloadUtility setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
	public DownloadUtility writeBodyFromFile(String fileName){
		this.files.add(fileName);
		return this;
	}
	
	public DownloadUtility writeBodyFromBytes(byte[] bytes){
		this.bytes.add(bytes);
		return this;
	}
	
	public DownloadUtility writeBodyFromString(String string){
		this.strings.add(string);
		return this;
	}
	
	
	
	
	public void startDownload() throws Exception{
		
		if(StringUtility.isNotEmpty(contentType)){
			response.setContentType(contentType);
		} else{
			response.setContentType("application/x-msdownload");
		}
		String fileEncodeName;
		if(StringUtility.isNotEmpty(fileName)){
			fileEncodeName = URLEncoder.encode(fileName, "UTF8");
		} else{
			fileEncodeName = URLEncoder.encode(fileName, "UTF8");
		}
		
		if(StringUtility.isNullOrBlank(fileExtension)){
			response.setHeader("Content-Disposition","attachment;filename="+fileEncodeName);
		} else{
			response.setHeader("Content-Disposition","attachment;filename="+fileEncodeName+"." +fileExtension);
		}
		ServletOutputStream out = response.getOutputStream();
		
		
		registerLog();
		
		
		writeFiles(out);
		writeStrings(out);
		writeBytes(out);
		//out.flush();
		//response.flushBuffer();
		out.close();
	}
	
	public void imageView() throws Exception{
		
		if(StringUtility.isNotEmpty(contentType)){
			response.setContentType(contentType);
		} else{
			response.setContentType("image/*");
		}
		
		ServletOutputStream out = response.getOutputStream();
		
		
		registerLog();
		
		
		writeFiles(out);
		writeStrings(out);
		writeBytes(out);
		//out.flush();
		//response.flushBuffer();
		out.close();
	}
	
	
	private void registerLog(){
		try{
			/*if(SessionUtility.getUserInfo() == null) return;
			
			HttpServletRequest request = WebUtility.getRequest();
			ServiceCall service = new ServiceCall("accessService","registerLog");
			service.callMethod(HashGenUtility
					.<String,Object>create()
					.add("_SESSION_GRUP_",SessionUtility.getUserInfo().getGRUP())
					.add("_SESSION_SEL_COMP_",SessionUtility.getUserInfo().getSEL_COMP())
					.add("_SESSION_COMP_",SessionUtility.getUserInfo().getCOMP())
					.add("_SESSION_EMPNO_",SessionUtility.getUserInfo().getEMPNO())
					.add("POW",SessionUtility.getUserInfo().getPOW())
					.add("MODE","F")
					.add("MENU_ID",request.getParameter("menuid"))
					.add("FILE_NM",fileName)
					.add("CLIENT_IP",WebUtility.getClientIpAddress(request))
					.toMap("p"));*/
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private void writeBytes(ServletOutputStream out) throws Exception{
		for(byte[] bs : this.bytes){
			InputStream in = null;
			try{
				
				in = new ByteArrayInputStream(bs);
				
				byte[] outputByte = new byte[4096];
				//copy binary contect to output stream
				int content;
				while((content = in.read(outputByte, 0, 4096)) != -1)
				{
					out.write(outputByte, 0, content);
				}
				out.flush();
			}catch(Exception e){
				
			} finally{
				if (in != null) {
	                try {
	                	in.close();
	                } catch (IOException e) {
	                    throw e;
	                }
	            }
			}
		}
	}
	
	private void writeFiles(ServletOutputStream out) throws Exception{
		for(String fileName : this.files){
			
			FileInputStream fileInputStream = null;
			try{
				File file = new File(fileName);
				byte[] bFile = new byte[(int) file.length()];
				fileInputStream = new FileInputStream(file);
				byte[] outputByte = new byte[4096];
				//copy binary contect to output stream
				int content;
				while((content = fileInputStream.read(outputByte, 0, 4096)) != -1)
				{
					out.write(outputByte, 0, content);
				}
				out.flush();
			}catch(Exception e){
				
			} finally{
				if (fileInputStream != null) {
	                try {
	                    fileInputStream.close();
	                } catch (IOException e) {
	                    throw e;
	                }
	            }
			}
		}
		
		 
         
	}
	
	private void writeStrings(ServletOutputStream out) throws Exception{
		for(String string : this.strings){
			StringBuffer sb = new StringBuffer(string);
			out.write(sb.toString().getBytes("UTF-8"));
			out.flush();
		}
	}
	
	

	public String getFileName() {
		return fileName;
	}

	public DownloadUtility setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	
	public DownloadUtility setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
		return this;
	}

	public String getFileExtension() {
		return fileExtension;
	}
}