package ccs.cmn.service.cat;

import java.util.List;
import java.util.Map;

public interface CatProfileService {

	public Map<String, Object> selectCatProfile(Map<String, Object> param);

	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);
}
