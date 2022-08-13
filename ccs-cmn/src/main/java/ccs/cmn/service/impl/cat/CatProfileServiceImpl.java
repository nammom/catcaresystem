package ccs.cmn.service.impl.cat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ccs.cmn.mapper.CmnMapper;
import ccs.cmn.mapper.cat.CatProfileMapper;
import ccs.cmn.service.cat.CatProfileService;


@Service(value="CatProfileService")
public class CatProfileServiceImpl implements CatProfileService {

	@Resource(name="CatProfileMapper")
	private CatProfileMapper catProfileMapper;

	@Resource(name="CmnMapper")
	private CmnMapper cmnMapper;
	
	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> selectCatProfile(Map<String, Object> param) {
		String group_yn = cmnMapper.selectCatGroupYn(Long.valueOf(String.valueOf(param.get("cat_cd"))));
		List<Map<String, Object>> list;
		if (StringUtils.equals(group_yn, "Y")) {
			list = catProfileMapper.selectCatGrpProfile(param);
		}else {
			list = catProfileMapper.selectCatProfile(param);
		}
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * ����� �ǰ� ���� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param) {
		return catProfileMapper.selectCatHealthy(param);
	}

	/**
	 *  ���� ��� or ����
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> saveCare(Map<String, Object> param) {
		Map<String, Object> status = new HashMap<>();
		Integer cnt = catProfileMapper.selectCareCount(param);
		if(cnt == 1) {
			catProfileMapper.deleteCare(param);
			status.put("status", "N");
		}else {
			catProfileMapper.insertCare(param);
			status.put("status", "Y");
		}
		return status;
	}
	
	/**
	 *  ���ã�� ��� or ����
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> saveBookMark(Map<String, Object> param) {
		Map<String, Object> status = new HashMap<>();
		Integer cnt = catProfileMapper.selectBookMarkCount(param);
		if(cnt == 1) {
			catProfileMapper.deleteBookMark(param);
			status.put("status", "N");
		}else {
			catProfileMapper.insertBookMark(param);
			status.put("status", "Y");
		}
		return status;
	}

	/**
	 * ���� ��� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCareList(Map<String, Object> param) {
		return catProfileMapper.selectCareList(param);
	}

	/**
	 * ���ã�� ��� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param) {
		return catProfileMapper.selectBookMarkList(param);
	}





}
