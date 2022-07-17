package br.com.firedev.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiIndexController {

	@GetMapping
	public String index() {
		return "API ok!";
	}
}
