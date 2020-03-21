package io.spring.buildingrestservices.controller;

import io.spring.buildingrestservices.domain.Employee;
import io.spring.buildingrestservices.assembler.EmployeeModelAssembler;
import io.spring.buildingrestservices.exception.EmployeeNotFoundException;
import io.spring.buildingrestservices.repository.EmployeeRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository,
                              EmployeeModelAssembler assembler) {
        this.assembler = assembler;
        this.repository = repository;
    }

//    ######################################################################

//    @GetMapping("/employee/{id}")
//    public Employee one(@PathVariable Long id){
//        return repository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException(id));
//    }

//    *********************HATEOAS************************
//    @GetMapping("/employee/{id}")
//    public EntityModel<Employee> one(@PathVariable Long id){
//        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//
//        return new EntityModel<>(employee,
//                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
//    }

    //Simplifying Link Creation
    @GetMapping("/employee/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

//    ######################################################################

//    @GetMapping("/employees")
//    public List<Employee> all(){
//        return repository.findAll();
//    }

//    #*******************HATEOAS******************
//    @GetMapping("/employees")
//    public CollectionModel<EntityModel<Employee>> all(){
//        List<EntityModel<Employee>> employees = repository.findAll()
//                .stream()
//                .map(employee -> new EntityModel<>(employee,
//                        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
//                .collect(Collectors.toList());
//
//        return new CollectionModel<>(employees,
//                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
//
//    }

    //Simplifying Link Creation
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

//    ######################################################################

//    @PostMapping("/employee")
//    public Employee newEmployee(@RequestBody Employee newEmployee){
//        return repository.save(newEmployee);
//    }

    @PostMapping("/employee")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

//    #######################################################################

//    @PutMapping("employee/{id}")
//    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
//        return repository.findById(id)
//        .map((employee)-> {
//            employee.setName(newEmployee.getName());
//            employee.setRole(newEmployee.getRole());
//            return repository.save(employee);
//        })
//        .orElseGet(() -> {
//            newEmployee.setId(id);
//            return repository.save(newEmployee);
//        });
//    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        Employee updateEmployee = repository.findById(id)
            .map(employee -> {
                employee.setName(newEmployee.getName());
                employee.setRole(newEmployee.getRole());
                return repository.save(employee);
            })
            .orElseGet(() ->{
                newEmployee.setId(id);
                return repository.save(newEmployee);
            });

        EntityModel<Employee> entityModel = assembler.toModel(updateEmployee);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

//    ############################################################################

//    @DeleteMapping("employee/{id}")
//    public void deleteEmployee(@PathVariable Long id){
//        repository.deleteById(id);
//    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
