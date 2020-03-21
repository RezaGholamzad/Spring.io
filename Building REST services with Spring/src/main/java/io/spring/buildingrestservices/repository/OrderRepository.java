package io.spring.buildingrestservices.repository;

import io.spring.buildingrestservices.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
