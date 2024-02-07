package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.exceptions.GameAlreadyExistExpections;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.services.GamesService;

@SpringBootTest
class GamesUnitTests {
    
    @InjectMocks
    private GamesService gamesService;

    @Mock
    private GamesRepository gamesRepository;

    @Test
    void givenGameAlreadyExist_whenCreatingGame_thenThrowError(){
        GamesDTO gameDTO = new GamesDTO("name", "image", 1, 1000);
        doReturn(true).when(gamesRepository).existsByName(any());

        GameAlreadyExistExpections exception = assertThrows(GameAlreadyExistExpections.class,
            () -> gamesService.save(gameDTO));

        assertNotNull(exception);
        assertEquals("Game already exists", exception.getMessage());
        verify(gamesRepository, times(0)).save(any());
        verify(gamesRepository, times(1)).existsByName(any());
    }

    @Test
    void givenValidGame_whenCreatingGame_thenSaveGame(){
        GamesDTO gameDTO = new GamesDTO("name", "image", 1, 1000);
        GamesModel newGame = new GamesModel(gameDTO);

        doReturn(false).when(gamesRepository).existsByName(any());
        doReturn(newGame).when(gamesRepository).save(any());

        GamesModel result = gamesService.save(gameDTO);

        assertNotNull(result);
        verify(gamesRepository, times(1)).existsByName(any());
        verify(gamesRepository, times(1)).save(any());
        assertEquals(newGame, result);


    }
}
