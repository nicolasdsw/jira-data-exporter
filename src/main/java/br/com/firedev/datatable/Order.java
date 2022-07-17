package br.com.firedev.datatable;

import lombok.Data;

@Data
public class Order {
	private Integer column;
	private Dir dir;

	public enum Dir {
		asc, desc;
	}
}
