package br.com.firedev.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.firedev.web.worklogs.WorklogController;
import br.com.firedev.web.worklogs.WorklogFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping({ "/" })
public class HomePage {

	private final WorklogController worklogController;

	@GetMapping
	public String index(ModelMap mm) {
		mm.addAttribute("welcome", "Olá");
		return "index";
	}

	@RequestMapping("/worklogs")
	public String list(ModelMap model, WorklogFilter filter, @SortDefault("author") Pageable pageable) {
		model.addAttribute("page", this.worklogController.index(filter, pageable));
		return "worklogs/worklogs";
	}
}
