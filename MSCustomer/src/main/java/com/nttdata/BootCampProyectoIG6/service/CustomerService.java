package com.nttdata.BootCampProyectoIG6.service;

import com.nttdata.BootCampProyectoIG6.model.Customer;
import com.nttdata.BootCampProyectoIG6.model.CustomerType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> createCustomer(Mono<Customer> c);
    Mono<Customer> findByCustomerId(String id);
    Flux<Customer> findAllCustomer();
    Mono<Customer> updateCustomer(Customer c);
    Mono<Customer> deleteCustomer(String id);
}
