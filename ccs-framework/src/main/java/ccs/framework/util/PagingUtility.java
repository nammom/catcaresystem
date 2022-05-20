package ccs.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.github.pagehelper.PageHelper;

import ccs.framework.model.DataTableInfoVO;

public class PagingUtility<E> {
	
	private DataTableInfoVO<E> pageInfo;

	
	public static <E> PagingUtility<E> create() {
		return new PagingUtility<E>();
	}
	
	/**
	 * paging
	 * @param <E>
	 * @param param
	 * @return
	 */
	public <E> DataTableInfoVO<E> startPage(Map<String, Object> param) {
		List<Map<String,Object>> order = (List<Map<String,Object>>)param.get("order");
		if(!CollectionUtils.isEmpty(order)){
			startPageOrdering(param);
		}else {
			startPage((int)param.get("draw"), (int)param.get("start"), (int)param.get("length"));
		}
		return (DataTableInfoVO<E>) this.pageInfo;
	}
	
	/**
	 * paging + ����
	 * @param param
	 * @return
	 */
	private void startPageOrdering(Map<String, Object> param) {
		List<Map<String,Object>> columns = (List<Map<String,Object>>)param.get("columns");
		List<Map<String,Object>> order = (List<Map<String,Object>>)param.get("order");
		String orderStr = getOrderByStr(columns, order);
		startPage((int)param.get("draw"), (int)param.get("start"), (int)param.get("length"), orderStr);
	}
	
	/**
	 * paging(+����)��  PageInfoVO ����, ����
	 * @param draw
	 * @param start
	 * @param length
	 * @param orderStr
	 */
	private void startPage(int draw, int start, int length, String orderStr) {
		startPage(start, length, orderStr);
		createPageInfo(draw);
	}
	
	/**
	 * paging ��  PageInfoVO ����, ����
	 * @param draw
	 * @param start
	 * @param length
	 */
	private void startPage(int draw, int start, int length) {
		startPage(start,length);
		createPageInfo(draw);
	}
	
	/**
	 * paging
	 * @param start
	 * @param length
	 */
	private void startPage(int start, int length) {
		if(length == -1) return;
		start = start/length + 1;
		PageHelper.startPage(start, length);
	}
	
	/**
	 * paging + ����
	 * @param start
	 * @param length
	 * @param orderStr
	 */
	private void startPage(int start, int length, String orderStr) {
		if(length == -1) return;
		start = start/length + 1;
		PageHelper.startPage(start, length, orderStr);
	}
	
	/**
	 * PageInfoVO ����
	 * @param <E>
	 * @param draw
	 * @return
	 */
	private <E> DataTableInfoVO<E> createPageInfo(int draw) {
		this.pageInfo = new DataTableInfoVO<>(draw);
		return (DataTableInfoVO<E>) this.pageInfo;
	}

	/**
	 * ���� ���ڿ� ����
	 * @param columns
	 * @return
	 */
	private String getOrderByStr(List<Map<String,Object>> columns, List<Map<String,Object>> order) {
		ArrayList<String> orderList = order.stream()
				.map(x->getOrderByStrFromMap(columns, x))
				.collect(Collectors.toCollection(ArrayList::new));
		return String.join(" ", orderList);
	}
	
	/**
	 * ���� ���� map�� ���ڿ��� ��ȯ
	 * @param columns
	 * @param map
	 * @return
	 */
	private String getOrderByStrFromMap(List<Map<String,Object>> columns, Map<String,Object> map) {
		int index = (int)map.get("column");
		String dir = (String)map.get("dir");
		Map<String,Object> col = (Map<String,Object>)columns.get(index);
		return (String)col.get("name") + " " + dir;
	}
}
