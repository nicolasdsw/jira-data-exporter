package br.com.firedev.web.epics;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.firedev.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/epics")
public class EpicController {

	private final IssueRepository repository;
	private final EpicMapper mapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Find epics", description = "Find epics by filters in JIRA", tags = { "epics" })
	@EpicFilterQP
	public List<EpicResDTO> index(@Parameter(required = false, hidden = true) EpicFilter filter) {
		var spec = this.mapper.toSpec(filter);
		var list = this.repository.findAll(spec);
		return list.stream().map(mapper::toDto).toList();
	}

	@GetMapping("/export.xlsx")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Export epics", description = "Export epics in JIRA", tags = { "epics" })
	@EpicFilterQP
	public ResponseEntity<byte[]> exportXlsx(@Parameter(required = false, hidden = true) EpicFilter filter) {
		var spec = this.mapper.toSpec(filter);
		var list = this.repository.findAll(spec);
		return this.mapper.toExcel(list).export();
	}

}
