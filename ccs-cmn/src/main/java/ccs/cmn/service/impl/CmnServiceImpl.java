package ccs.cmn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ccs.cmn.mapper.CmnMapper;
import ccs.cmn.service.CmnService;

@Service(value="CmnService")
public class CmnServiceImpl implements CmnService {

	@Resource(name="CmnMapper")
	CmnMapper cmnMapper;

	@Override
	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return  cmnMapper.selectCmnCodeList(param);
	}

}
