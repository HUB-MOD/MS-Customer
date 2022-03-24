package com.nttdata.BootCampProyectoIG6.controller;

import com.nttdata.BootCampProyectoIG6.model.Customer;
import com.nttdata.BootCampProyectoIG6.model.CustomerType;
import com.nttdata.BootCampProyectoIG6.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.ws.rs.PathParam;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody Mono<Customer> customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping(value = "/getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Customer> findAll(){
        return customerService.findAllCustomer();
    }

    @GetMapping("/{id}")
    @ResponseStatus
    public ResponseEntity<Mono<Customer>> findById(@PathVariable("id") String id){
        Mono<Customer> customerMono = customerService.findByCustomerId(id);
        return new ResponseEntity<Mono<Customer>>(customerMono, customerMono !=null? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Mono<Customer>> updateCust(@RequestBody Customer customer){
        Mono<Customer> customerMono = customerService.updateCustomer(customer);
        return new ResponseEntity<Mono<Customer>>(customerMono, customerMono!=null? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Customer> deleteCust(@PathVariable("id") String id){
        return customerService.deleteCustomer(id);
    }

}
