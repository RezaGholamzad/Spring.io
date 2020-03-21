package io.spring.buildingrestservices;

import io.spring.buildingrestservices.domain.Employee;
import io.spring.buildingrestservices.domain.Order;
import io.spring.buildingrestservices.domain.Status;
import io.spring.buildingrestservices.repository.EmployeeRepository;
import io.spring.buildingrestservices.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabaseEmployee(EmployeeRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo" ,"Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));

        };
    }

    @Bean
    CommandLineRunner initDatabaseOrder(OrderRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Order("MacBook Pro", Status.COMPLETED)));
            log.info("Preloading " + repository.save(new Order("iPhone", Status.IN_PROGRESS)));

        };
    }
}
