package io.spring.accessingjpadatawithrest;

import io.spring.accessingjpadatawithrest.domain.Person;
import io.spring.accessingjpadatawithrest.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabaseEmployee(PersonRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Person("Bilbo" ,"Baggins")));
            log.info("Preloading " + repository.save(new Person("Frodo", "Baggins")));
        };
    }
}
