package io.spring.corsRESTfulWebService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
//            }
//        };
//    }
}
