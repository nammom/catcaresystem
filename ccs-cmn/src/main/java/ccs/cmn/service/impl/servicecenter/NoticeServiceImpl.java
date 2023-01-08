package ccs.cmn.service.impl.servicecenter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ccs.cmn.mapper.servicecenter.NoticeMapper;
import ccs.cmn.service.servicecenter.NoticeService;


@Service(value="NoticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource(name="NoticeMapper")
	private NoticeMapper noticeMapper;

	/**
	 * 공지사항 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> param){
		return noticeMapper.selectNoticeList(param);
	}

}
