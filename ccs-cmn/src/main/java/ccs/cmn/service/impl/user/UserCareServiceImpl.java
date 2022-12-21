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
	 * 사용자 고양이 돌봄 목록  조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserCatCareList(Map<String, Object> data) {
		return userCareMapper.selectUserCatCareList(data);
	}

	/**
	 * 사용자 서식지 돌봄 목록  조회
	 * @param data
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectUserHabitatCareList(Map<String, Object> data) {
		return userCareMapper.selectUserHabitatCareList(data);
	}

}
