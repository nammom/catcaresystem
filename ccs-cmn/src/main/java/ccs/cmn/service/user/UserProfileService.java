package ccs.cmn.service.user;

import java.util.List;
import java.util.Map;

import ccs.framework.model.FileParameter;

public interface UserProfileService {
	
	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectUserProfile(Map<String, Object> param);

	/**
	 * ����� ������ ���� ��ȸ (÷������ ����Ʈ ����)
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> selectUserProfileDetail(Map<String, Object> param) throws Exception;
	
	/**
	 * ����� ������ ���� ����
	 * @param param
	 */
	public void saveUserProfile(Map<String, Object> param, FileParameter fileParameter) throws Exception;

	/**
	 * �г��� �ߺ� üũ
	 * @return
	 */
	public boolean checkNickname(Map<String, Object> param);
}
