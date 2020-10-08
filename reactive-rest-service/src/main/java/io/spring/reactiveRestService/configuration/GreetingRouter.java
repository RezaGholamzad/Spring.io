package io.spring.reactiveRestService.configuration;

import io.spring.reactiveRestService.component.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GreetingRouter {

    //we use a router to handle the only route we expose ("/hello")
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler){
        /*
            The router listens for traffic on the /hello path and returns the value
            provided by our reactive handler class.
         */
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
    }
}
