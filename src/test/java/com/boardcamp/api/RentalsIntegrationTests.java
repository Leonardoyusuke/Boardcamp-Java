package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentalsIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalsRepository rentalsRepository;

    @Autowired
    private GamesRepository gamesRepository;

    @BeforeEach
    @AfterEach
    public void cleanUp() {   
        rentalsRepository.deleteAll();
        customerRepository.deleteAll();
        gamesRepository.deleteAll();
     
    }

    @Test
    void givenInvalidUserId_whenCreatingRental_thenThrowError(){
        GamesModel game = new GamesModel(null,"name", "image",2,1000);
        gamesRepository.save(game);
        HttpEntity<RentalsDTO> newRental = new HttpEntity<>(new RentalsDTO(1L, game.getId(), 1));

        ResponseEntity<String> responseRental = restTemplate.exchange("/rentals", HttpMethod.POST, newRental, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseRental.getStatusCode());
        assertEquals("Customer not found", responseRental.getBody());
    }

    @Test
    void givenInvalidGameId_whenCreatingRental_thenThrowError(){
        CustomerModel customer = new CustomerModel(null,"name", "12345678910");
        customerRepository.save(customer);
        HttpEntity<RentalsDTO> newRental = new HttpEntity<>(new RentalsDTO(customer.getId(), 1L, 1));

        ResponseEntity<String> responseRental = restTemplate.exchange("/rentals", HttpMethod.POST, newRental, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseRental.getStatusCode());
        assertEquals("Game not found", responseRental.getBody());
    }

    @Test
    void givenInvalidStock_whenCreatingRental_thenThrowError(){
        CustomerModel customer = new CustomerModel(null,"name", "12345678910");
        customerRepository.save(customer);
        GamesModel game = new GamesModel(null,"name", "image",1,1000);
        gamesRepository.save(game);
        HttpEntity<RentalsDTO> rental = new HttpEntity<>(new RentalsDTO(customer.getId(), game.getId(), 2));
        restTemplate.exchange("/rentals", HttpMethod.POST, rental, String.class);

        HttpEntity<RentalsDTO> newRental = new HttpEntity<>(new RentalsDTO(customer.getId(), game.getId(), 2));
        ResponseEntity<String> responseNewRental = restTemplate.exchange("/rentals", HttpMethod.POST, newRental, String.class);


        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseNewRental.getStatusCode());
        assertEquals("Game out of stock", responseNewRental.getBody());
    }

    @Test
    void givenValidRental_whenCreatingRental_thenSaveRental(){
        CustomerModel customer = new CustomerModel(null,"name", "12345678910");
        customerRepository.save(customer);
        GamesModel game = new GamesModel(null,"name", "image",2,1000);
        gamesRepository.save(game);
        HttpEntity<RentalsDTO> newRental = new HttpEntity<>(new RentalsDTO(customer.getId(), game.getId(), 1));

        ResponseEntity<RentalsModel> responseRental = restTemplate.exchange("/rentals", HttpMethod.POST, newRental, RentalsModel.class);

        assertEquals(HttpStatus.CREATED, responseRental.getStatusCode());
        assertEquals(customer.getId(), responseRental.getBody().getCustomer().getId());
        assertEquals(game.getId(), responseRental.getBody().getGame().getId());
        assertEquals(1000, responseRental.getBody().getOriginalPrice());
    }
}

