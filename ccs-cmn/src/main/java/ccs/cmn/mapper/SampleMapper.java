package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;


@Mapper(value="SampleMapper")
public interface SampleMapper {

	public List<Map<String, Object>> selectSampleSql();

}
