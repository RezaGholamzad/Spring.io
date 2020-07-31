package io.spring.asynchronousMethods.service;

import io.spring.asynchronousMethods.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/*
     	Creating a local instance of the GitHubLookupService class does NOT allow the findUser method
     	to run asynchronously. It must be created inside a @Configuration class or picked up
     	by @ComponentScan.
 */
@Service
@Slf4j
public class GitHubLookupService {

    /*
        The GitHubLookupService class uses Spring’s RestTemplate to invoke a remote REST point (api.github.com/users/)
        and then convert the answer into a User object. Spring Boot automatically provides
        a RestTemplateBuilder that customizes the defaults with any auto-configuration bits (that is, MessageConverter).
     */
    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /*
        The findUser method is flagged with Spring’s @Async annotation,
        indicating that it should run on a separate thread. The method’s return type is
        CompletableFuture<User> instead of User, a requirement for any asynchronous service.
        This code uses the completedFuture method to return a CompletableFuture instance
        that is already completed with result of the GitHub query.
     */
    @Async
    public CompletableFuture<User> findUser (String user) throws InterruptedException {
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        Thread.sleep(5000L);
        return CompletableFuture.completedFuture(results);
    }
}
