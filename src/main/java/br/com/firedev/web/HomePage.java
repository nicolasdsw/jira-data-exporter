package br.com.firedev.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping({ "/" })
public class HomePage {

	@GetMapping
	public String index(ModelMap mm) {
		mm.addAttribute("welcome", "Olá Mundo!");
		return "index";
	}
}
