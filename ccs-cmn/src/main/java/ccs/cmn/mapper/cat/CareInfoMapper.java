package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CareInfoMapper")
public interface CareInfoMapper {

	/**
	 * 고양이 돌봄 정보 목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectCareInfoList(Map<String, Object> param);

	/**
	 * 돌봄 정보 등록
	 * @param param
	 * @return
	 */
	public void insertCareInfo(Map<String, Object> param);

	/**
	 * 돌봄 정보 수정
	 * @param param
	 */
	public void updateCareInfo(Map<String, Object> param);

	/**
	 * 돌봄 정보 삭제
	 * @param param
	 * @return
	 */
	public void deleteCareInfo(Map<String, Object> param);


	
}
