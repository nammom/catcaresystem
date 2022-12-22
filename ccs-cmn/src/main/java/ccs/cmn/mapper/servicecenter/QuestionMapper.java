package ccs.cmn.mapper.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "QuestionMapper")
public interface QuestionMapper {

//	/**
//	 * ����� ������ ���� ��ȸ
//	 * @param param
//	 * @return
//	 */
//	public List<Map<String, Object>> selectUserProfile(Map<String, Object> param);
//
//	/**
//	 * ����� �г��� ����
//	 * @param param
//	 */
//	public void updateNickname(Map<String, Object> param);

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

//	/**
//	 * �г��� ���� ��ȸ - �ߺ� üũ
//	 * @return
//	 */
//	public Integer selectNicknameCount(Map<String, Object> param);

}
