package br.com.firedev.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class RangeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5124845047962594494L;
	private LocalDate start;
	private LocalDate end;
}
