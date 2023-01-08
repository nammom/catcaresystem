package ccs.cmn.service.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface QuestionService {

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

}
