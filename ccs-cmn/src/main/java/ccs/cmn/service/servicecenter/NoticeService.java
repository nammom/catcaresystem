package ccs.cmn.service.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface NoticeService {
	
	/**
	 * �������� ��� ��ȸ
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> param);

}
