package io.spring.corsRESTfulWebService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CorsRestfulWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorsRestfulWebServiceApplication.class, args);
    }

//    Global CORS configuration
//    You can combine global- and controller-level CORS configuration.
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000")
//                  .allowedMethods("GET", "POST");
//            }
//        };
//    }
}
