package br.com.firedev.web.worklogs;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorklogResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2870402740882036098L;
	private String author;
	private String project;
	private String issue;
	private LocalDateTime startdate;
	private Long timeworked;
	private String body;

	public static Map<String, String> sortFields() {
		var map = new HashMap<String, String>();
		map.put("author", "authorUsername");
		map.put("project", "issue.project.pname");
		map.put("issue", "issue.issuenum");
		map.put("timeworked", null);
		return map;
	}
}
