package br.com.firedev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.firedev.util.ContextUtil;

@SpringBootApplication
public class JiraDataExporterApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(JiraDataExporterApplication.class, args);
		ContextUtil.setContext(context);
	}
}
