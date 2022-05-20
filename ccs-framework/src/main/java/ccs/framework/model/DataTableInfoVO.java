package ccs.framework.model;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DataTableInfoVO<E> {

	private Integer draw;
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<E> data;
	
	public void setPageInfo(List<E> data) {
		if(data instanceof Page) {
			Page<E> page = (Page)data;
			this.recordsTotal = (int)page.getTotal();
			this.recordsFiltered = (int)page.getTotal();
		}else {
			this.recordsTotal = data.size();
			this.recordsFiltered = data.size();
		}
		this.data = (List<E>) data;
	}
	
	public DataTableInfoVO(List<E> data) {
		this.data = (List<E>) data;
	}

	public DataTableInfoVO(Integer draw) {
		this.draw = draw;
	}
	
	
	
	
}
