package ccs.cmn.mapper.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "QuestionMapper")
public interface QuestionMapper {

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

}
