package ccs.cmn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ccs.cmn.mapper.SampleMapper;
import ccs.cmn.service.SampleService;

@Service(value="SampleService")
public class SampleServiceImpl implements SampleService {

	@Resource(name="SampleMapper")
	SampleMapper samplemapper;

	@Override
	public List<Map<String, Object>> selectSampleSql() {
		// TODO Auto-generated method stub
		return samplemapper.selectSampleSql();
	}

}
