package br.com.firedev.web.worklogs;

import java.util.List;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.firedev.dto.OptionDTO;
import br.com.firedev.repository.IssueRepository;
import br.com.firedev.repository.ProjectRepository;
import br.com.firedev.repository.UserRepository;
import br.com.firedev.repository.WorklogRepository;
import br.com.firedev.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/worklogs")
public class WorklogController {

	private final WorklogRepository repository;
	private final UserRepository userRepository;
	private final IssueRepository issueRepository;
	private final ProjectRepository projectRepository;
	private final WorklogMapper mapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find Worklogs", description = "Find worklogs by filters in JIRA", tags = { "worklogs" })
	@WorklogFilterQP
	@PageableAsQueryParam
	public Page<WorklogResDTO> index(@Parameter(required = false, hidden = true) WorklogFilter filter,
			@Parameter(required = false, hidden = true) Pageable pageable) {
		var spec = this.mapper.toSpec(filter);
		var list = this.repository.findAll(spec, PageUtil.validateAndReplaceSorts(WorklogResDTO.sortFields(), pageable));
		return list.map(mapper::toDto);
	}

	@GetMapping("/export.xlsx")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Export Worklogs", description = "Export worklogs by filters in JIRA", tags = { "worklogs" })
	@WorklogFilterQP
	public ResponseEntity<byte[]> exportXlsx(@Parameter(required = false, hidden = true) WorklogFilter filter) {
		var spec = this.mapper.toSpec(filter);
		var list = this.repository.findAll(spec);
		return this.mapper.toExcel(list).export();
	}

	@GetMapping("/filter-options/users")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find filter options users", description = "Find filter options", tags = { "worklogs" })
	public List<OptionDTO<Long>> filterOptionsUsers() {
		return this.userRepository.findAll().stream().map(OptionDTO::of).toList();
	}

	@GetMapping("/filter-options/projects")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find filter options projects", description = "Find filter options", tags = { "worklogs" })
	public List<OptionDTO<Long>> filterOptionsProjects() {
		return this.projectRepository.findAll().stream().map(OptionDTO::of).toList();
	}

	@GetMapping("/filter-options/issues")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find filter options issues", description = "Find filter options", tags = { "worklogs" })
	public List<OptionDTO<Long>> filterOptionsIssues() {
		return this.issueRepository.findAll().stream().map(OptionDTO::of).toList();
	}
}
