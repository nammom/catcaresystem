package ccs.cmn.service.impl.cat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ccs.cmn.mapper.cat.CatProfileMapper;
import ccs.cmn.service.cat.CatProfileService;


@Service(value="CatProfileService")
public class CatProfileServiceImpl implements CatProfileService {

	@Resource(name="CatProfileMapper")
	CatProfileMapper catProfileMapper;

	@Override
	public Map<String, Object> selectCatProfile(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = catProfileMapper.selectCatProfile(param);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return catProfileMapper.selectCatHealthy(param);
	}

}
