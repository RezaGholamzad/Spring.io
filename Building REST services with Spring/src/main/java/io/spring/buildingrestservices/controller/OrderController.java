package io.spring.buildingrestservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.spring.buildingrestservices.assembler.OrderModelAssembler;
import io.spring.buildingrestservices.domain.Order;
import io.spring.buildingrestservices.domain.Status;
import io.spring.buildingrestservices.exception.OrderNotFoundException;
import io.spring.buildingrestservices.repository.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private OrderRepository repository;
    private OrderModelAssembler assembler;

    public OrderController(OrderRepository repository, OrderModelAssembler assembler) {
        this.assembler = assembler;
        this.repository = repository;
    }


    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all (){
        List<EntityModel<Order>> orders = repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/order/{id}")
    public EntityModel<Order> one(@PathVariable Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    @PostMapping("/order")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order){
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = repository.save(order);

        return ResponseEntity.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<RepresentationModel> cancel(@PathVariable Long id){
        Order order = repository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new VndErrors.VndError("Method not allowed",
                    "You can't cancel an order that is in the " + order.getStatus() + " status"));

    }

    @PutMapping("/order/{id}")
    public ResponseEntity<RepresentationModel> complete(@PathVariable Long id){
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "You can't complete an order that is in the " + order.getStatus() + " status"));
    }



}
