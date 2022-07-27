package ccs.cmn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ccs.cmn.mapper.CmnMapper;
import ccs.cmn.service.CmnService;

@Service(value="CmnService")
public class CmnServiceImpl implements CmnService {

	@Resource(name="CmnMapper")
	CmnMapper cmnMapper;

	/**
	 * ���� �ڵ� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return  cmnMapper.selectCmnCodeList(param);
	}

	/**
	 * ���� �ڵ� ��ȸ
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectAreaCodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return cmnMapper.selectAreaCodeList(param);
	}

	/**
	 * ����� ���� ��ȸ
	 * @param target_cd
	 * @return
	 */
	@Override
	public Map<String, Object> selectCatArea(Long target_cd) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = cmnMapper.selectCatArea(target_cd);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	/**
	 * ����� ���� ���� ��ȸ
	 * @param target_cd
	 * @return
	 */
	@Override
	public String selectCatGroupYn(Long target_cd) {
		return cmnMapper.selectCatGroupYn(target_cd);
	}
	/**
	 * ����� �˻� ��� ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSearchCatList(Map<String, Object> data) {
		return cmnMapper.selectSearchCatList(data);
	}

	/**
	 * ������ �˻� ��� ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSearchHabitatList(Map<String, Object> data) {
		return cmnMapper.selectSearchHabitatList(data);
	}

	/**
	 * �׺���̼� �޴� ��� ��ȸ
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectMenuList() {
		// TODO Auto-generated method stub
		return cmnMapper.selectMenuList();
	}

	/**
	 * �׺���̼� ����� etc �޴� ��� ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectEtcMenuList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return cmnMapper.selectEtcMenuList(data);
	}

}
