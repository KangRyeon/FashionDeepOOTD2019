package com.example.demo;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import dao.MemberDao;
import dao.ClothesDao;
import dao.FashionSetDao;

//@EnableWebMvc
//@ComponentScan("com.post.javaconfig")
@Configuration
public class JavaConfig {
	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/closet?characterEncoding=utf8&serverTimezone=UTC");
		ds.setUsername("root");
		ds.setPassword("1234");
		return ds;
	}
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}

	@Bean
	public ClothesDao clothesDao() {
		return new ClothesDao(dataSource());
	}
	
	@Bean
	public FashionSetDao fashion_setDao() {
		return new FashionSetDao(dataSource());
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}

	@Bean
	public PythonExec pythonExec() {
		return new PythonExec("D:\\python_D\\fashion_project\\serverfile\\", "D:\\spring_kkr\\");
	}
}