package br.com.firedev.datatable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Column {

	private String data;
	private String name;
	private Boolean searchable;
	private Boolean orderable;
	private Search search;

	public Column(String data) {
		this.data = data;
	}
}
