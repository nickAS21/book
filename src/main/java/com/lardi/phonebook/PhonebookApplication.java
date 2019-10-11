package com.lardi.phonebook;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;

//http://www.thymeleaf.org/doc/articles/layouts.html
@SpringBootApplication
public class PhonebookApplication {

	/**
	 * путь к которому должен передаваться в качестве аргументов JVM машине (-Dlardi.conf=/path/to/file.properties).
	 * mvn clean package
	 * java -jar -Dlardi.conf="{pathToFile}application.properties" ./target/phonebook-lardi-test.jar
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String prop = System.getProperty("lardi.conf");
		SpringApplicationBuilder builder = new SpringApplicationBuilder(PhonebookApplication.class);

		if (prop != null && !prop.equals("") && new File(prop).exists()) {
			builder.properties("spring.config.location=" + prop);
		}
		builder.run(args);
	}

	@Bean
	public ClassLoaderTemplateResolver templateResolver() {

		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode("HTML5");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");

		return viewResolver;
	}
}
