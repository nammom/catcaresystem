package ccs.cmn.service.impl.care;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ccs.cmn.mapper.care.CareMapper;
import ccs.cmn.mapper.care.CharacterMapper;
import ccs.cmn.service.FileService;
import ccs.cmn.service.UploadFileService;
import ccs.cmn.service.care.CareService;


@Service(value="CareService")
public class CareServiceImpl implements CareService {
	
	@Resource(name="CareMapper")
	private CareMapper careMapper;

	/**
	 * 돌봄 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCareList(Map<String, Object> param) {
		return careMapper.selectCareList(param);
	}

	/**
	 *  돌봄 등록 or 해제
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> saveCare(Map<String, Object> param) {
		Map<String, Object> status = new HashMap<>();
		Integer cnt = careMapper.selectCareCount(param);
		if(cnt == 1) {
			careMapper.deleteCare(param);
			status.put("status", "N");
		}else {
			careMapper.insertCare(param);
			status.put("status", "Y");
		}
		return status;
	}
	
}
