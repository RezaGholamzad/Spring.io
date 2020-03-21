package io.spring.buildingrestservices.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    private String role;
//    private String name;

    // new need
    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName, String role) {
//        this.name = name;

        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getName(){
        return this.firstName + " " + this.lastName;
    }

    public void setName(String name){
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
    }
}
