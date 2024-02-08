package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    void givenRepeatedCPF_whenCreatingCustomer_thenThrowError(){
        CustomerModel customer = new CustomerModel(null,"name", "12345678910");
        customerRepository.save(customer);
        HttpEntity<CustomerDTO> newCustomer = new HttpEntity<>(new CustomerDTO("name2", "12345678910"));

        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.POST, newCustomer, String.class);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("CPF already exists", response.getBody());
    }
    @Test
    void givenValidCustomer_whenCreatingCustomer_thenSaveCustomer(){
        HttpEntity<CustomerDTO> newCustomer = new HttpEntity<>(new CustomerDTO("name", "12345678910"));

        ResponseEntity<CustomerModel> response = restTemplate.exchange("/customers", HttpMethod.POST, newCustomer, CustomerModel.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("name", response.getBody().getName());
        assertEquals("12345678910", response.getBody().getCpf());
    }  
}

