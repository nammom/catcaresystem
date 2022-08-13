package ccs.cmn.service.impl.cat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ccs.cmn.mapper.cat.CatGroupMapper;
import ccs.cmn.service.cat.CatGroupService;


@Service(value="CatGroupService")
public class CatGroupServiceImpl implements CatGroupService {

	@Resource(name="CatGroupMapper")
	private CatGroupMapper catGroupMapper;

	/**
	 * 고양이 그룹 정보 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data) {
		if(StringUtils.equals("grp", (String)data.get("groupFlag"))) {
			return catGroupMapper.selectCatGroupList(data);
		}else {
			return catGroupMapper.selectCatGroupMemberList(data);
		}
	}

	/**
	 * 고양이 그룹 등록
	 * @param data
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertCatGroup(Map<String, Object> data) {
		List<Map<String, Object>> saveList = (List<Map<String, Object>>)data.get("saveList");
		if(saveList.size() > 0) {
			for(Map<String, Object> e : saveList) {
				e.put("target_cd", (Integer)data.get("target_cd"));
				e.put("_SESSION_USER_CD_", (Long)data.get("_SESSION_USER_CD_"));
				if(StringUtils.equals("grp", (String)data.get("groupFlag"))) {
					catGroupMapper.insertCatGroup(e);
				}else {
					catGroupMapper.insertCatGroupMember(e);
				}
			}
		}
	}

	/**
	 * 고양이 그룹 삭제
	 * @param data
	 */
	@Override
	public void deleteCatGroup(Map<String, Object> data) {
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)data.get("deleteList");
		if(deleteList.size() > 0) {
			if(StringUtils.equals("grp", (String)data.get("groupFlag"))) {
				catGroupMapper.deleteCatGroup(data);
			}else {
				catGroupMapper.deleteCatGroupMember(data);
			}
		}
	}


}
