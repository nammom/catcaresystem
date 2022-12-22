package ccs.cmn.service.servicecenter;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface QuestionService {
	
//	/**
//	 * ����� ������ ���� ��ȸ
//	 * @param param
//	 * @return
//	 */
//	public Map<String, Object> selectUserProfile(Map<String, Object> param);
//
//	/**
//	 * ����� ������ ���� ��ȸ (÷������ ����Ʈ ����)
//	 * @param param
//	 * @return
//	 * @throws Exception 
//	 */
//	public Map<String, Object> selectUserProfileDetail(Map<String, Object> param) throws Exception;
//	
	/**
	 * 1:1 ����  ���� ����
	 * @param param
	 * @param fileParameter
	 * @throws Exception
	 */
	public void saveQuestion(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * 1:1 ���� ��� ��ȸ
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectQuestionList(Map<String, Object> param) throws Exception ;

//	/**
//	 * �г��� �ߺ� üũ
//	 * @return
//	 */
//	public boolean checkNickname(Map<String, Object> param);
}
