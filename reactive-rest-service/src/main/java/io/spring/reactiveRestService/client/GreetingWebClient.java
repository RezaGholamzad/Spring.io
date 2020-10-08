package io.spring.reactiveRestService.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GreetingWebClient {

    /*
        The Spring MVC RestTemplate class is, by nature, blocking. Consequently,
        we don’t want to use it in a reactive application. For reactive applications,
        Spring offers the WebClient class, which is non-blocking. We’ll use a WebClient
        implementation to consume our RESTful service:

        WebClient can be used to communicate with non-reactive, blocking services, too.
     */
    private final WebClient client = WebClient.create("http://localhost:8080");

    private final Mono<ClientResponse> result = client.get()
            .uri("/hello")
            .accept(MediaType.TEXT_PLAIN)
            .exchange();

    /*
        The WebClient uses reactive features, in the form of a Mono to hold the content
        of the URI we specify and a function (in the getResult method) to turn that content
        into a string. If we had different requirements, we might turn it into something other
        than a string. Since we’re going to put the result into System.out, a string will do here.
     */
    public String getResult(){
        return "result : " + result.flatMap(clientResponse ->
        clientResponse.bodyToMono(String.class)).block();
    }

}
