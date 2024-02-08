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

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GamesIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GamesRepository gamesRepository;


    @AfterEach
    public void cleanUp() {
        gamesRepository.deleteAll();
    }

    @Test
    void givenRepeatedGame_whenCreatingGame_thenThrowError(){
        gamesRepository.save(new GamesModel(null,"name", "image",2,1000));
        HttpEntity<GamesDTO> newGame = new HttpEntity<>(new GamesDTO("name","name", 2,1000));

        ResponseEntity<String> response = restTemplate.exchange("/games", HttpMethod.POST, newGame, String.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Game already exists", response.getBody());
    }

    @Test
    void givenValidGame_whenCreatingGame_thenSaveGame(){
        HttpEntity<GamesDTO> newGame = new HttpEntity<>(new GamesDTO("name","image", 2,1000));

        ResponseEntity<GamesModel> response = restTemplate.exchange("/games", HttpMethod.POST, newGame, GamesModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("name", response.getBody().getName());
        assertEquals("image", response.getBody().getImage());
        assertEquals(2, response.getBody().getStockTotal());
        assertEquals(1000, response.getBody().getPricePerDay());
    }
    
}
