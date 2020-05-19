package io.spring.accessingdatamongodb.repository;

import io.spring.accessingdatamongodb.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    /*
    Spring Data MongoDB uses the MongoTemplate to execute the queries behind your find* methods.
        You can use the template yourself for more complex queries
     */
    Customer findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
}
