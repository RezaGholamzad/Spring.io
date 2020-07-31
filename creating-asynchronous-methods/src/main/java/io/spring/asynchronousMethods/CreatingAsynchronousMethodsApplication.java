package io.spring.asynchronousMethods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
/*
    The @EnableAsync annotation switches on Spring’s ability to run @Async methods
    in a background thread pool.
 */
@EnableAsync
public class CreatingAsynchronousMethodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreatingAsynchronousMethodsApplication.class, args);
    }

    /*
         This class also customizes the Executor by defining a new bean. Here, the method
         is named taskExecutor, since this is the specific method name for which Spring searches.
         By default, Spring will be searching for an associated thread pool definition:
         either a unique TaskExecutor bean in the context,
         or an Executor bean named "taskExecutor" otherwise.
         If neither of the two is resolvable, a SimpleAsyncTaskExecutor will be used
         to process async method invocations.
         In our case, we want to limit the number of concurrent threads to two and limit
         the size of the queue to 500. There are many more things you can tune.
     */
    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

}
