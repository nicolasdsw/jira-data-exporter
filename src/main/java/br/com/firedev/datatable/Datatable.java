package br.com.firedev.datatable;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Datatable<T> {

	private List<T> data;
	private int recordsFiltered;
	private int recordsTotal;
	private int draw;

	public Datatable(List<T> data) {
		this.data = data;
	}

	public Datatable(PagingRequest pagingRequest, Page<T> data) {
		this.data = data.getContent();
		this.recordsFiltered = (int) data.getTotalElements();
		this.recordsTotal = (int) data.getTotalElements();
		this.draw = pagingRequest.getDraw();
	}
}
