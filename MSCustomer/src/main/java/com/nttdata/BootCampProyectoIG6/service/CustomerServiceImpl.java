package com.nttdata.BootCampProyectoIG6.service;

import com.nttdata.BootCampProyectoIG6.model.Customer;
import com.nttdata.BootCampProyectoIG6.model.CustomerType;
import com.nttdata.BootCampProyectoIG6.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

//    Function<Customer, Mono<Void>> f = deleteCustomer -> customerRepository.delete(deleteCustomer);

    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customerMono) {
        return customerMono.flatMap(customerRepository::insert).log();
    }

    @Override
    public Mono<Customer> findByCustomerId(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Flux<Customer> findAllCustomer() {
        return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<Customer> updateCustomer(Customer c) {
        return customerRepository.save(c);
    }

    @Override
    public Mono<Customer> deleteCustomer(String id) {
        return customerRepository.findById(id)
                .flatMap(deleteCustomer -> customerRepository.delete(deleteCustomer)
                .then(Mono.just(deleteCustomer)));
    }

}
