package io.spring.corsRESTfulWebService.controller;

import io.spring.corsRESTfulWebService.model.Greeting;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    You can customize this behavior by specifying the value of one of the following annotation attributes:
//    origins
//    methods
//    allowedHeaders
//    exposedHeaders
//    allowCredentials
//    maxAge(a maxAge of 30 minutes is used).
//    You can also add the @CrossOrigin annotation at the controller class level as well,
//    to enable CORS on all handler methods of this class.
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

//    @GetMapping("/greeting-javaconfig")
//    public Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
//        System.out.println("==== in greeting ====");
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }
}
