package ccs.cmn.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ccs.cmn.mapper.user.UserBookMarkMapper;
import ccs.cmn.service.user.UserBookMarkService;


@Service(value="UserBookMarkService")
public class UserBookMarkServiceImpl implements UserBookMarkService {

	@Resource(name="UserBookMarkMapper")
	private UserBookMarkMapper userBookMarkMapper;

	/**
	 * ����� ����� ���ã�� ���  ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserCatBookMarkList(Map<String, Object> data) {
		return userBookMarkMapper.selectUserCatBookMarkList(data);
	}

	/**
	 * ����� ������ ���ã�� ���  ��ȸ
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserHabitatBookMarkList(Map<String, Object> data) {
		return userBookMarkMapper.selectUserHabitatBookMarkList(data);
	}

}
