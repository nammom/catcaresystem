package ccs.cmn.mapper.care;

import java.util.List;
import java.util.Map;

import ccs.framework.annotations.Mapper;

@Mapper(value = "BookMarkMapper")
public interface BookMarkMapper {

	/**
	 * ���ã�� ����(����) ��ȸ
	 * @param param
	 * @return
	 */
	public Integer selectBookMarkCount(Map<String, Object> param);

	/**
	 * ���ã�� ����
	 * @param param
	 */
	public void deleteBookMark(Map<String, Object> param);

	/**
	 * ���ã�� ���
	 * @param param
	 */
	public void insertBookMark(Map<String, Object> param);
	
	/**
	 * ���ã�� ��� ��ȸ
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectBookMarkList(Map<String, Object> param);


}
