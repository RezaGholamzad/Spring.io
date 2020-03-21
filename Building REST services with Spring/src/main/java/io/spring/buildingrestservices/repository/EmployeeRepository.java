package io.spring.buildingrestservices.repository;

import io.spring.buildingrestservices.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
