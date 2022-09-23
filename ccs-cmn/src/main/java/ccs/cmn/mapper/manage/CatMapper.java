package ccs.cmn.mapper.manage;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value="CatMapper")
public interface CatMapper {

	/**
	 * 고양이 목록 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatList(Map<String, Object> data);

	/**
	 * 고양이 상세 조회
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> selectCatByCd(Map<String, Object> data);

	/**
	 * 무리 고양이에 소속된 고양이 수
	 * @param data
	 * @return
	 */
	public Integer selectCatGrpMemberCount(Map<String, Object> data);

	/**
	 * 고양이 저장
	 * @param param
	 */
	public void insertCat(Map<String, Object> param);

	/**
	 * 고양이 프로필 저장
	 * @param param
	 */
	public void insertCatProfile(Map<String, Object> param);

	/**
	 * 고양이 그룹 프로필 저장
	 * @param param
	 */
	public void insertCatGrpProfile(Map<String, Object> param);

	/**
	 * 고양이 수정
	 * @param param
	 */
	public void updateCat(Map<String, Object> param);

	/**
	 * 고양이 프로필 수정
	 * @param param
	 */
	public void updateCatProfile(Map<String, Object> param);

	/**
	 * 고양이 그룹 프로필 수정
	 * @param param
	 */
	public void updateCatGrpProfile(Map<String, Object> param);

	/**
	 * 고양이 삭제
	 * @param param
	 */
	public void deleteCat(Map<String, Object> param);

	/**
	 * 고양이 프로필 삭제
	 * @param param
	 */
	public void deleteCatProfile(Map<String, Object> param);

	/**
	 * 고양이 그룹 프로필 삭제
	 * @param param
	 */
	public void deleteCatGrpProfile(Map<String, Object> param);

}
