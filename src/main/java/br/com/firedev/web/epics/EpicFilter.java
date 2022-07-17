package br.com.firedev.web.epics;

import java.io.Serializable;

import br.com.firedev.dto.RangeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EpicFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3381014334982710482L;
	private Long id;
	private String epic;
	private RangeDTO created;
	private RangeDTO duedate;
}
