package ccs.cmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import ccs.cmn.mapper.CmnMapper;
import ccs.cmn.service.CmnService;

@Service(value="CmnService")
public class CmnServiceImpl implements CmnService {

	@Resource(name="CmnMapper")
	CmnMapper cmnMapper;

	/**
	 * 공통 코드 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return  cmnMapper.selectCmnCodeList(param);
	}

	/**
	 * 지역 코드 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectAreaCodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return cmnMapper.selectAreaCodeList(param);
	}

	/**
	 * 고양이 지역 조회
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
	 * 고양이 무리 여부 조회
	 * @param target_cd
	 * @return
	 */
	@Override
	public String selectCatGroupYn(Long target_cd) {
		return cmnMapper.selectCatGroupYn(target_cd);
	}
	/**
	 * 고양이 검색 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSearchCatList(Map<String, Object> data) {
		return cmnMapper.selectSearchCatList(data);
	}

	/**
	 * 서식지 검색 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSearchHabitatList(Map<String, Object> data) {
		return cmnMapper.selectSearchHabitatList(data);
	}

	/**
	 * 네비게이션 메뉴 목록 조회
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectMenuList() {
		// TODO Auto-generated method stub
		return cmnMapper.selectMenuList();
	}

	/**
	 * 네비게이션 모바일 etc 메뉴 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectEtcMenuList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return cmnMapper.selectEtcMenuList(data);
	}

	/**
	 * 서식지, 고양이 관리 사이드 네비게이션 메뉴 목록 조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectManageMenuList(Map<String, Object> data) throws Exception{
		List<Map<String, Object>> targetInfoList;
		if (StringUtils.isEmpty((String)data.get("target_type")) || StringUtils.equals((String)data.get("target_type"), "cat")) {
			targetInfoList = cmnMapper.selectCatMenuInfoList(data);
		} else {
			targetInfoList = cmnMapper.selectTargetMenuInfoList(data);
		}
		
		if ( CollectionUtils.isEmpty(targetInfoList) ) {
			return null;
		}
		Map<String, Object> targetInfoMap = targetInfoList.get(0);
		List<Map<String, Object>> menuList = cmnMapper.selectManageMenuList(targetInfoMap);
		return this.createManageMenuList(targetInfoMap, menuList);

	}

	/**
	 * menu url 정보 셋팅
	 * @param infoMap
	 * @param menuList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> createManageMenuList(Map<String, Object> infoMap, List<Map<String, Object>> menuList) throws Exception{
		for( Map<String, Object> menu : menuList ) {
			String url = (String)menu.get("menu_link");
			for( String key : infoMap.keySet() ) {
				if(StringUtils.isNotBlank(url)) {
					if(url.contains("{" + key + "}")) {
						url = url.replace("{" + key + "}", String.valueOf(infoMap.get(key)));
					}
				}
			}
			menu.replace("menu_link", url);
		}
		return menuList;
	}
}
