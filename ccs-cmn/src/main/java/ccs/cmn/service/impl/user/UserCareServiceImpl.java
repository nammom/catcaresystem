package ccs.cmn.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ccs.cmn.mapper.user.UserCareMapper;
import ccs.cmn.service.user.UserCareService;


@Service(value="UserCareService")
public class UserCareServiceImpl implements UserCareService {

	@Resource(name="UserCareMapper")
	private UserCareMapper userCareMapper;

	/**
	 * ����� ����� ���� ���  ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserCatCareList(Map<String, Object> data) {
		return userCareMapper.selectUserCatCareList(data);
	}

	/**
	 * ����� ������ ���� ���  ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserHabitatCareList(Map<String, Object> data) {
		return userCareMapper.selectUserHabitatCareList(data);
	}

}
