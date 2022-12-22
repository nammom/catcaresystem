package ccs.cmn.service.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface QuestionService {
	
//	/**
//	 * 사용자 프로필 정보 조회
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> selectUserProfile(Map<String, Object> param);
//
//	/**
//	 * 사용자 프로필 정보 조회 (첨부파일 리스트 포함)
//	 * @param param
//	 * @return
//	 * @throws Exception 
//	 */
//	public Map<String, Object> selectUserProfileDetail(Map<String, Object> param) throws Exception;
//	
	/**
	 * 1:1 문의  정보 수정
	 * @param param
	 * @param fileParameter
	 * @throws Exception
	 */
	public void saveQuestion(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 1:1 문의 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectQuestionList(Map<String, Object> param) throws Exception ;

//	/**
//	 * 닉네임 중복 체크
//	 * @return
//	 */
//	public boolean checkNickname(Map<String, Object> param);
}
