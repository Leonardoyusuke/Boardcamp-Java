package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.exceptions.GameAlreadyExistExpections;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

@Service
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

    public List<GamesModel> getAllGames(){
        return gamesRepository.findAll();
    }


}