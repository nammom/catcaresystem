package ccs.framework.dataservice;

public interface IdGnrService {

	//String getStringId() throws Exception;
	
	Object getId() throws Exception;
	
	void initialize() throws Exception;
	
	void destroy() throws Exception;
}
