package ccs.cmn.mapper.user;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "UserProfileMapper")
public interface UserProfileMapper {

	/**
	 * ����� ������ ���� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectUserProfile(Map<String, Object> param);

	/**
	 * ����� �г��� ����
	 * @param param
	 */
	public void updateNickname(Map<String, Object> param);

	/**
	 * ����� ������ ����
	 * @param param
	 */
	public void updateProfile(Map<String, Object> param);

	/**
	 * �г��� ���� ��ȸ - �ߺ� üũ
	 * @return
	 */
	public Integer selectNicknameCount(Map<String, Object> param);

}
