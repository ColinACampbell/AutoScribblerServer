package tech.eazley.AutoScribbler.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String localhost = "http://localhost:4200";
        String prod = "https://auto-scribbler-app.herokuapp.com";
        registry.addMapping("/**").allowedOrigins(prod);
       // WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
