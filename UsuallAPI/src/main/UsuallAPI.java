package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "geo")
@ComponentScan(basePackages = "main.config")
public class UsuallAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(UsuallAPI.class, args);
	}

}
