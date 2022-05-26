package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;


import ccs.framework.annotations.Mapper;


@Mapper(value="CmnMapper")
public interface CmnMapper {

	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param);

}
