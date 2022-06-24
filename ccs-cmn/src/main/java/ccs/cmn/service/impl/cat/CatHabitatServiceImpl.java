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
import ccs.cmn.mapper.cat.CatHabitatMapper;
import ccs.cmn.service.cat.CatGroupService;
import ccs.cmn.service.cat.CatHabitatService;


@Service(value="CatHabitatService")
public class CatHabitatServiceImpl implements CatHabitatService {

	@Resource(name="CatHabitatMapper")
	CatHabitatMapper catHabitatMapper;

	/**
	 * 고양이 서식지 정보 조회 
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCatHabitatList(Map<String, Object> data) {
		return catHabitatMapper.selectCatHabitatList(data);
	}

	/**
	 * 고양이 서식지 등록
	 * @param data
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertCatHabitat(Map<String, Object> data) {
		List<Map<String, Object>> saveList = (List<Map<String, Object>>)data.get("saveList");
		if(saveList.size() > 0) {
			for(Map<String, Object> e : saveList) {
				e.put("cat_cd", (Integer)data.get("cat_cd"));
				e.put("_SESSION_USER_CD_", (Long)data.get("_SESSION_USER_CD_"));
				catHabitatMapper.insertCatHabitat(e);
			}
		}
	}

	/**
	 *  고양이 서식지 삭제
	 * @param data
	 */
	@Override
	public void deleteCatHabitat(Map<String, Object> data) {
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)data.get("deleteList");
		if(deleteList.size() > 0) {
			catHabitatMapper.deleteCatHabitat(data);
		}
	}


}
