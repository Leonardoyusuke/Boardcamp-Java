package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.exceptions.GameOutOfStockExpections;
import com.boardcamp.api.exceptions.UserNotFoundExpections;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class RentalsService {
    
    final RentalsRepository rentalsRepository;
    final GamesRepository gamesRepository;
    final CustomerRepository customersRepository;
    
    RentalsService(RentalsRepository rentalsRepository, GamesRepository gamesRepository, CustomerRepository customersRepository) {
        this.rentalsRepository = rentalsRepository;
        this.gamesRepository = gamesRepository;
        this.customersRepository = customersRepository;
    }

    public RentalsModel save(RentalsDTO dto) {
        GamesModel game = gamesRepository.findById(dto.getGameId()).orElseThrow(() -> new UserNotFoundExpections("Game not found"));
        CustomerModel customer = customersRepository.findById(dto.getCustomerId()).orElseThrow(() -> new UserNotFoundExpections("Customer not found"));

        if(rentalsRepository.countByGameId(dto.getGameId()) >= game.getStockTotal()){
            throw new GameOutOfStockExpections("Game out of stock");
        }
      
        int priceTotal = (int)(game.getPricePerDay() * dto.getDaysRented());
        
        RentalsModel rental = new RentalsModel(dto,game,customer,priceTotal);
        return rentalsRepository.save(rental);
    }

    public List <RentalsModel> findAll() {
        return rentalsRepository.findAll();
    }
}
