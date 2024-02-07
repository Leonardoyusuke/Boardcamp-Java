package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.exceptions.GameNotFoundExpections;
import com.boardcamp.api.exceptions.GameOutOfStockExpections;
import com.boardcamp.api.exceptions.RentalAlreadyFinished;
import com.boardcamp.api.exceptions.RentalNotFoundExpections;
import com.boardcamp.api.exceptions.UserNotFoundExpections;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;
import com.boardcamp.api.services.RentalsService;

@SpringBootTest
class RentalsUnitTests {
    
    @InjectMocks
    private RentalsService rentalsService;

    @Mock 
    private RentalsRepository rentalsRepository;

    @Mock
    private GamesRepository gamesRepository;

    @Mock
    private CustomerRepository customersRepository;

    @Test
    void givenInvalidGame_whenCreatingRental_thenThrowError(){
        RentalsDTO rentalDTO = new RentalsDTO(1L, 1L, 1);
        doReturn(Optional.empty()).when(gamesRepository).findById(any());
        
        GameNotFoundExpections exception = assertThrows(GameNotFoundExpections.class,
            () -> rentalsService.save(rentalDTO));

        assertNotNull(exception);
        assertEquals("Game not found", exception.getMessage());
        verify(gamesRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
    }

    @Test
    void givenInvalidCustomer_whenCreatingRental_thenThrowError(){
        RentalsDTO rentalDTO = new RentalsDTO(1L, 1L, 1);
        GamesModel game = new GamesModel();
        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(Optional.empty()).when(customersRepository).findById(any());
        
        UserNotFoundExpections exception = assertThrows(UserNotFoundExpections.class,
            () -> rentalsService.save(rentalDTO));

        assertNotNull(exception);
        assertEquals("Customer not found", exception.getMessage());
        verify(gamesRepository, times(1)).findById(any());
        verify(customersRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
    }

    @Test
    void givenGameOutOfStock_whenCreatingRental_thenThrowError(){
        CustomerModel customer = new CustomerModel(1l,"name", "12345678911");
        GamesModel game = new GamesModel(1l,"name", "image", 1, 1000);
        RentalsDTO rentalDTO = new RentalsDTO(1L, 1L, 1);

        //  RentalsModel newRental = new RentalsModel(rentalDTO,game, customer, 10000); //aqui

        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(Optional.of(customer)).when(customersRepository).findById(any());
        doReturn(2).when(rentalsRepository).countByGameIdAndReturnDateIsNull(any());
        
        GameOutOfStockExpections exception = assertThrows(GameOutOfStockExpections.class,
            () -> rentalsService.save(rentalDTO));

        assertNotNull(exception);
        assertEquals("Game out of stock", exception.getMessage());
        verify(gamesRepository, times(1)).findById(any());
        verify(customersRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
    }

    @Test
    void givenValidRent_whenCreatingRental_thenSaveRent(){
        CustomerModel customer = new CustomerModel(1l,"name", "12345678911");
        GamesModel game = new GamesModel(1l,"name", "image", 2, 1000);
        RentalsDTO rentalDTO = new RentalsDTO(1L, 1L, 1);
        RentalsModel newRental = new RentalsModel(rentalDTO,game, customer, 10000);

        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(Optional.of(customer)).when(customersRepository).findById(any());
        doReturn(1).when(rentalsRepository).countByGameIdAndReturnDateIsNull(any());
        doReturn(newRental).when(rentalsRepository).save(any());
        
        RentalsModel result = rentalsService.save(rentalDTO);

        assertNotNull(result);
        verify(gamesRepository, times(1)).findById(any());
        verify(customersRepository, times(1)).findById(any());
        verify(rentalsRepository, times(1)).save(any());
        assertEquals(newRental, result);
    }
    
    @Test
    void givenInvalidRental_whenFinishingRental_thenThrowError(){
        doReturn(Optional.empty()).when(rentalsRepository).findById(any());
        
        RentalNotFoundExpections exception = assertThrows(RentalNotFoundExpections.class,
            () -> rentalsService.finish(1L));

        assertNotNull(exception);
        assertEquals("Rental not found", exception.getMessage());
        verify(rentalsRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
    }

    @Test
    void givenRentalAlreadyFinished_whenFinishingRental_thenThrowError(){
        RentalsModel rental = new RentalsModel();
        rental.setReturnDate(java.time.LocalDate.now());
        doReturn(Optional.of(rental)).when(rentalsRepository).findById(any());
        
        RentalAlreadyFinished exception = assertThrows(RentalAlreadyFinished.class,
            () -> rentalsService.finish(1L));

        assertNotNull(exception);
        assertEquals("Rental already finished", exception.getMessage());
        verify(rentalsRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
    }

    @Test
    void givenValidRental_whenFinishingRental_thenFinishRentalWithDelayFee(){
        RentalsModel rental = new RentalsModel();
        rental.setRentDate(java.time.LocalDate.now().minusDays(5));
        rental.setDaysRented(3);
        rental.setGame(new GamesModel(1l,"name", "image", 2, 1000));
        doReturn(Optional.of(rental)).when(rentalsRepository).findById(any());
        doReturn(rental).when(rentalsRepository).save(any());
        
        RentalsModel result = rentalsService.finish(1L);

        assertNotNull(result);
        verify(rentalsRepository, times(1)).findById(any());
        verify(rentalsRepository, times(1)).save(any());
        assertEquals(2000, result.getDelayFee());
        assertEquals(java.time.LocalDate.now(), result.getReturnDate());
    }

    @Test
    void givenValidRental_whenFinishingRental_ThenFinishRental(){
        RentalsModel rental = new RentalsModel();
        rental.setRentDate(java.time.LocalDate.now().minusDays(2));
        rental.setDaysRented(3);
        rental.setGame(new GamesModel(1l,"name", "image", 2, 1000));
        doReturn(Optional.of(rental)).when(rentalsRepository).findById(any());
        doReturn(rental).when(rentalsRepository).save(any());
        
        RentalsModel result = rentalsService.finish(1L);

        assertNotNull(result);
        verify(rentalsRepository, times(1)).findById(any());
        verify(rentalsRepository, times(1)).save(any());
        assertEquals(0, result.getDelayFee());
        assertEquals(java.time.LocalDate.now(), result.getReturnDate());
    }
}
