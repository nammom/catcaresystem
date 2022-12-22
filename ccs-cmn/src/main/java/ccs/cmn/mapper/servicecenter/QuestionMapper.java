package ccs.cmn.mapper.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "QuestionMapper")
public interface QuestionMapper {

//	/**
//	 * 사용자 프로필 정보 조회
//	 * @param param
//	 * @return
//	 */
//	public List<Map<String, Object>> selectUserProfile(Map<String, Object> param);
//
//	/**
//	 * 사용자 닉네임 수정
//	 * @param param
//	 */
//	public void updateNickname(Map<String, Object> param);

	/**
	 * 1:1 문의  정보 수정
	 * @param param
	 */
	public void insertQuestion(Map<String, Object> param);

	/**
	 * 1:1 문의  목록 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectQuestionList(Map<String, Object> param);

//	/**
//	 * 닉네임 갯수 조회 - 중복 체크
//	 * @return
//	 */
//	public Integer selectNicknameCount(Map<String, Object> param);

}
