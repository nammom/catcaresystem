package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatGroupMapper")
public interface CatGroupMapper {

	/**
	 * 고양이 그룹 멤버 정보 조회 
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupMemberList(Map<String, Object> data);

	/**
	 * 고양이 그룹 정보 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatGroupList(Map<String, Object> data);

	
}
