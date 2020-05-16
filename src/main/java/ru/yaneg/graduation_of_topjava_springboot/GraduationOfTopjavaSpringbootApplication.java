package ru.yaneg.graduation_of_topjava_springboot;

import net.sf.ehcache.hibernate.EhCacheRegionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.yaneg.graduation_of_topjava_springboot.security.AppProperties;

@SpringBootApplication
@EnableCaching
public class GraduationOfTopjavaSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationOfTopjavaSpringbootApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
}
