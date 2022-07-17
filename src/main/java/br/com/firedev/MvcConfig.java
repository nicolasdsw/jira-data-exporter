package br.com.firedev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
//		viewControllerRegistry.addViewController("/").setViewName("redirect:/pages/dashboard");
	}
}
