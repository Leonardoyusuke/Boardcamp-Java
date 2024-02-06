package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerCPFConflictExpections;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.services.CustomerService;

@SpringBootTest
class CustomerUnitTests {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	void givenRepeatedCPF_whenCreatingCustomer_thenThrowError() {
		CustomerDTO customerDTO = new CustomerDTO("name", "12345678910");
		doReturn(true).when(customerRepository).existsBycpf(any());

		CustomerCPFConflictExpections exception = assertThrows(CustomerCPFConflictExpections.class,
			() -> customerService.save(customerDTO));

		assertNotNull(exception);
		assertEquals("CPF already exists", exception.getMessage());
		verify(customerRepository, times(0)).save(any());
		verify(customerRepository, times(1)).existsBycpf(any());
	}

	@Test
	void givenValidCustomer_whenCreatingCustomer_thenSaveCustomer() {
		CustomerDTO customerDTO = new CustomerDTO("name", "12345678911");
		CustomerModel newCustomer = new CustomerModel(customerDTO);

		doReturn(false).when(customerRepository).existsBycpf(any());
		doReturn(newCustomer).when(customerRepository).save(any());

		CustomerModel result = customerService.save(customerDTO);

		assertNotNull(result);
		verify(customerRepository, times(1)).save(any());
		verify(customerRepository, times(1)).existsBycpf(any());
		assertEquals(newCustomer, result);
	}

	@Test
	void givenUserId_whenSearchingById_thenReturnUser() {
		Long id = 1L;
		CustomerModel customer = new CustomerModel();
		customer.setId(id);
		Optional<CustomerModel> optionalCustomer = Optional.of(customer);

		when(customerRepository.existsById(id)).thenReturn(true);
		when(customerRepository.findById(id)).thenReturn(optionalCustomer);

		Optional<CustomerModel> result = customerService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(customer, result.get());
	}

}
