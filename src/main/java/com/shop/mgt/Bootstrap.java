package com.shop.mgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 启动类添加Servlet支持
 */
@SpringBootApplication
public class Bootstrap extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Bootstrap.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}
}
