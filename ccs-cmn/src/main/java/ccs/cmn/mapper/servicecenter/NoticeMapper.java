package ccs.cmn.mapper.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "NoticeMapper")
public interface NoticeMapper {

	/**
	 * �������� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> param);

}
