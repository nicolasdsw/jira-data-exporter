package br.com.firedev.web.epics;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EpicResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781736536035635052L;
	private Long id;
	private String epic;
	private Long timeEstimated;
	private Long timeSpent;
	private Long timeAvailable;
	private LocalDate created;
	private LocalDate duedate;
	private Double deadlinePercentage;
	private Double productivityPercentage;
}
