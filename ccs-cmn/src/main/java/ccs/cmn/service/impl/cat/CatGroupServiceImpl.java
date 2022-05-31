package ccs.cmn.service.impl.cat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ccs.cmn.mapper.cat.CatGroupMapper;
import ccs.cmn.service.cat.CatGroupService;


@Service(value="CatGroupService")
public class CatGroupServiceImpl implements CatGroupService {

	@Resource(name="CatGroupMapper")
	CatGroupMapper catGroupMapper;

	/**
	 * ����� �׷� ��� ���� ��ȸ 
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatGroupMemberList(Map<String, Object> data) {
		return catGroupMapper.selectCatGroupMemberList(data);
	}

	/**
	 * ����� �׷� ���� ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data) {
		return catGroupMapper.selectCatGroupList(data);
	}


}
