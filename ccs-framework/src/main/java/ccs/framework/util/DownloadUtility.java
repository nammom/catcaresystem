package ccs.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.Setter;


public class DownloadUtility {
	private static final Logger logger = LogManager.getLogger(DownloadUtility.class);
	
	private HttpServletResponse response;
	
	@Getter
	private String contentType;
	@Getter
	private String fileName;
	@Getter
	private String fileExtension;
	
	private List<String> files;
	
	public DownloadUtility(HttpServletResponse response){
		this.response = response;
		this.files = new ArrayList<String>();
	}
	
	public static DownloadUtility create(HttpServletResponse response){
		return new DownloadUtility(response);
	}
	
	public DownloadUtility writeBodyFromFile(String filePath) throws IOException{
		this.files.add(filePath);
		Path source = Paths.get(filePath);
	    String mimeType = Files.probeContentType(source);
	    this.contentType = mimeType;
		return this;
	}
	
	
	public void startDownload() throws Exception{
		
		if(StringUtility.isNotEmpty(contentType)){
			response.setContentType(contentType);
		} else{
			response.setContentType("application/octet-stream");
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
			response.setHeader("Content-Disposition","attachment;filename="+fileEncodeName);
		}
		ServletOutputStream out = response.getOutputStream();
		
		
		registerLog();
		
		
		writeFiles(out);
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
		//out.flush();
		//response.flushBuffer();
		out.close();
	}
	
	
	private void registerLog(){
		try{
			
		} catch(Exception e){
			logger.warn(e);
		}
		
	}
	
	private void writeFiles(ServletOutputStream out) throws Exception{
		for(String filePath : this.files){
			
			FileInputStream fileInputStream = null;
			try{
				File file = new File(filePath);

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

	public DownloadUtility setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public DownloadUtility setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public DownloadUtility setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
		return this;
	}

	
	

}