package io.spring.accessingdatamongodb;

import io.spring.accessingdatamongodb.model.Customer;
import io.spring.accessingdatamongodb.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/*
Spring Boot automatically handles those repositories as long as they are included
    in the same package (or a sub-package) of your @SpringBootApplication class.
    For more control over the registration process, you can use the @EnableMongoRepositories annotation.

By default, @EnableMongoRepositories scans the current package for any interfaces
    that extend one of Spring Data’s repository interfaces.
    You can use its basePackageClasses=MyRepository.class to safely tell Spring Data MongoDB
    to scan a different root package by type if your project layout has multiple projects
    and it does not find your repositories.
 */
@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

    private final CustomerRepository repository;

    public AccessingDataMongodbApplication(CustomerRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataMongodbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }
}
