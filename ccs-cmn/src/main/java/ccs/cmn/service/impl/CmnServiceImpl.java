package ccs.cmn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	/**
	 * ������, ����� ���� ���̵� �׺���̼� �޴� ��� ��ȸ
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
		
		// ���� �޴� ��ȸ
		Map<String, Object> targetInfoMap = targetInfoList.get(0);
		List<Map<String, Object>> menuList = cmnMapper.selectManageMenuList(targetInfoMap);
		menuList = this.createMenuUrl(targetInfoMap, menuList);
		menuList = this.createTreeMenu(menuList);
		
		// �Խ��� �޴� ��ȸ
		List<Map<String, Object>> boardMenuList = cmnMapper.selectBoardMenuList(targetInfoMap);
		boardMenuList = this.createMenuUrl(targetInfoMap, boardMenuList);
		
		// list ��ġ��
        menuList.addAll(boardMenuList);
		
		return menuList;
	}

	/**
	 * Ʈ�� ���� �޴� ����
	 * @param menuList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> createTreeMenu(List<Map<String, Object>> menuList) throws Exception{
		
		Map<Long, Object> menuListMap = menuList.stream()
				.collect(Collectors.toMap(x-> (Long)x.get("menu_id"), x -> x));
		
		for ( int i = menuList.size() - 1; i >= 0; i-- ) {
			Map<String, Object> menuMap = menuList.get(i);
			// ���� �޴� Ȯ��
			Long memu_upper_id = (Long)menuMap.get("menu_upper_id");
			if( memu_upper_id != null ) {	
				// ���� �޴��� �ִ� ���
				Map<String, Object> menuUpperMap = (Map<String, Object>) menuListMap.get(memu_upper_id);
				// ���� �޴��� childList�� �߰�
				List<Map<String, Object>> childList = (List<Map<String, Object>>)menuUpperMap.getOrDefault("childList", new ArrayList<>());
				childList.add(0, menuMap);
				menuUpperMap.put("childList", childList);
				// menuList������ ����
				menuList.remove(i);
			}
		}
		return menuList;
	}
	
	/**
	 * menu url ���� ����
	 * @param infoMap
	 * @param menuList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> createMenuUrl(Map<String, Object> infoMap, List<Map<String, Object>> menuList) throws Exception{
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
