package com.boardcamp.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.services.GamesService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/games")
public class GamesController {
    
    final GamesService gamesService;
    
    GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping()
    public ResponseEntity <GamesModel> createGame(@RequestBody @Valid GamesDTO dto) {
        GamesModel game = gamesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping()
    public ResponseEntity <List<GamesModel>> getAllGames() {
        List<GamesModel> games = gamesService.getAllGames();
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }
    
    
    

}
