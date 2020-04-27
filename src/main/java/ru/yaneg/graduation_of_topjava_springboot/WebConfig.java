package ru.yaneg.graduation_of_topjava_springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.yaneg.graduation_of_topjava_springboot.ui.converter.DateTimeFormatters;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*");

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
    }
}
