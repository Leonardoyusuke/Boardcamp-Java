package com.boardcamp.api.services;

import org.springframework.stereotype.Repository;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.exceptions.GameAlreadyExistExpections;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

@Repository
public class GamesService {

    final GamesRepository gamesRepository ;
    
    GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public GamesModel save(GamesDTO dto){
        if(gamesRepository.existsByName(dto.getName())){
            throw new GameAlreadyExistExpections("Game already exists");
        }
        GamesModel game = new GamesModel(dto);
        return gamesRepository.save(game);
    }


}