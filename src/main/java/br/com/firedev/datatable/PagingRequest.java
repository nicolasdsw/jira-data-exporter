package br.com.firedev.datatable;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PagingRequest {

	private int start;
	private int length;
	private int draw;
	private List<Order> order;
	private List<Column> columns;
	private Search search;

	public Pageable toPageable() {
		var page = start / length;
//		var orders = order.stream().map(o -> org.springframework.data.domain.Sort.Order.by(o.getDir())).toList();
		return PageRequest.of(page, length, Sort.unsorted());
	}
}
