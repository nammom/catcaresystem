package ccs.cmn.service.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface NoticeService {
	
	/**
	 * 공지사항 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> param);

}
