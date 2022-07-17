package br.com.firedev.web.worklogs;

import java.io.Serializable;
import java.util.List;

import br.com.firedev.dto.RangeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorklogFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52937955092995595L;
	
	private RangeDTO startdate;
	private List<Long> authorIds;
	private Long issueId;
	private String issue;
	private Long projectId;
}
