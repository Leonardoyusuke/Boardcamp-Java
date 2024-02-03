package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.exceptions.GameOutOfStockExpections;
import com.boardcamp.api.exceptions.RentalAlreadyFinished;
import com.boardcamp.api.exceptions.RentalNotFoundExpections;
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

    RentalsService(RentalsRepository rentalsRepository, GamesRepository gamesRepository,
            CustomerRepository customersRepository) {
        this.rentalsRepository = rentalsRepository;
        this.gamesRepository = gamesRepository;
        this.customersRepository = customersRepository;
    }

    public RentalsModel save(RentalsDTO dto) {
        GamesModel game = gamesRepository.findById(dto.getGameId())
                .orElseThrow(() -> new UserNotFoundExpections("Game not found"));
        CustomerModel customer = customersRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new UserNotFoundExpections("Customer not found"));

        if (rentalsRepository.countByGameIdAndReturnDateIsNull(dto.getGameId()) >= game.getStockTotal()) {
            throw new GameOutOfStockExpections("Game out of stock");
        }

        int priceTotal = (game.getPricePerDay() * dto.getDaysRented());

        RentalsModel rental = new RentalsModel(dto, game, customer, priceTotal);
        return rentalsRepository.save(rental);
    }

    public List<RentalsModel> findAll() {
        return rentalsRepository.findAll();
    }

    public RentalsModel finish (Long id) {
        RentalsModel rental = rentalsRepository.findById(id).orElseThrow(() ->{ 
            throw new RentalNotFoundExpections("Rental not found");
        });
            
        if(rental.getReturnDate() != null){
            throw new RentalAlreadyFinished("Rental already finished");
        }

        int daysRented = rental.getDaysRented();
        LocalDate today = LocalDate.now();

        long daysWithGame = ChronoUnit.DAYS.between(rental.getRentDate(), today);

        if(daysWithGame > daysRented){
            int delayFee = (int) (daysWithGame - daysRented) * rental.getGame().getPricePerDay();
            rental.setDelayFee(delayFee);
        }else{
            rental.setDelayFee(0);
        }
        
        rental.setReturnDate(today);
        return rentalsRepository.save(rental);

    }

}
