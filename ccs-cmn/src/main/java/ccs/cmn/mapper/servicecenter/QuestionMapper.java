package ccs.cmn.mapper.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "QuestionMapper")
public interface QuestionMapper {

	/**
	 * 1:1 ����  ���� ����
	 * @param param
	 */
	public void insertQuestion(Map<String, Object> param);

	/**
	 * 1:1 ����  ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectQuestionList(Map<String, Object> param);

}
