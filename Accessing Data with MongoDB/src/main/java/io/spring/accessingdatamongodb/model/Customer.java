package io.spring.accessingdatamongodb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/*
MongoDB stores data in collections. Spring Data MongoDB maps the Customer class into
 a collection called customer. If you want to change the name of the collection,
  you can use Spring Data MongoDB’s @Document annotation on the class.
 */
@Data
@NoArgsConstructor
public class Customer {

//    id fits the standard name for a MongoDB ID, so it does not require any special annotation
//    to tag it for Spring Data MongoDB.
//    @Id
    public String id;

    public String firstName;
    public String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
