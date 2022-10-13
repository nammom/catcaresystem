package ccs.cmn.service.impl.care;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ccs.cmn.mapper.care.BookMarkMapper;
import ccs.cmn.mapper.care.CharacterMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.care.BookMarkService;


@Service(value="BookMarkService")
public class BookMarkServiceImpl implements BookMarkService {
	
	@Resource(name="BookMarkMapper")
	private BookMarkMapper bookMarkMapper;
	
	/**
	 * 즐겨찾기 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param) {
		return bookMarkMapper.selectBookMarkList(param);
	}

	/**
	 *  즐겨찾기 등록 or 해제
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> saveBookMark(Map<String, Object> param) {
		Map<String, Object> status = new HashMap<>();
		Integer cnt = bookMarkMapper.selectBookMarkCount(param);
		if(cnt == 1) {
			bookMarkMapper.deleteBookMark(param);
			status.put("status", "N");
		}else {
			bookMarkMapper.insertBookMark(param);
			status.put("status", "Y");
		}
		return status;
	}
	
}
