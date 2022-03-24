package com.nttdata.BootCampProyectoIG6;

import com.nttdata.BootCampProyectoIG6.controller.CustomerController;
import com.nttdata.BootCampProyectoIG6.model.Customer;
import com.nttdata.BootCampProyectoIG6.model.CustomerType;
import com.nttdata.BootCampProyectoIG6.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerController.class)
class BootCampProyectoIg6ApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private CustomerService customerService;

	@Test
	public void createCustomerTest() {
		Mono<Customer> customerMono = Mono.just(
				new Customer("1", "Heber Uraccahua", "DNI", "12345678", CustomerType.valueOf("PERSONAL")));
		when(customerService.createCustomer(customerMono)).thenReturn(customerMono);
		webTestClient.post().uri("/customer/create")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(customerMono), Customer.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void findAllCustomerTest() {
		Flux<Customer> customerFlux = Flux.just(
				new Customer("1", "Heber Uraccahua", "DNI", "12345678", CustomerType.valueOf("PERSONAL")),
				new Customer("2", "Heber Barrios", "DNI", "12345678", CustomerType.valueOf("BUSINESS")));
		when(customerService.findAllCustomer()).thenReturn(customerFlux);
		Flux<Customer> responseBody = webTestClient.get().uri("/customer/getAll")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Customer.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new Customer("1", "Heber Uraccahua", "DNI", "12345678", CustomerType.valueOf("PERSONAL")))
				.expectNext(new Customer("2", "Heber Barrios", "DNI", "12345678", CustomerType.valueOf("BUSINESS")))
				.verifyComplete();
	}
}
