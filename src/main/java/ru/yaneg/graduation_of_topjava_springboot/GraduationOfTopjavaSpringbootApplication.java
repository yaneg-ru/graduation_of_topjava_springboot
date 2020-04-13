package ru.yaneg.graduation_of_topjava_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.yaneg.graduation_of_topjava_springboot.security.AppProperties;

@SpringBootApplication
public class GraduationOfTopjavaSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraduationOfTopjavaSpringbootApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}

	@Bean(name="AppProperties")
	public AppProperties getAppProperties()	{return new AppProperties();}
}
