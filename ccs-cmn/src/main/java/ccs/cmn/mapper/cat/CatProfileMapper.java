package ccs.cmn.mapper.cat;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "CatProfileMapper")
public interface CatProfileMapper {

	public List<Map<String, Object>> selectCatProfile(Map<String, Object> param);

	public List<Map<String, Object>> selectCatHealthy(Map<String, Object> param);

}
