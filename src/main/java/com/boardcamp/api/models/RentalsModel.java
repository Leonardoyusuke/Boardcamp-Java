package com.boardcamp.api.models;

import java.time.LocalDate;
import java.util.Set;

import com.boardcamp.api.dtos.RentalsDTO;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalsModel {

    public RentalsModel(RentalsDTO dto, GamesModel game, CustomerModel customer,int  priceTotal ) {
        this.rentDate = LocalDate.now();        
        this.daysRented = dto.getDaysRented();
        this.returnDate = null;        
        this.originalPrice = priceTotal;
        this.delayFee = 0;
        this.customer = customer;
        this.game = game;
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private Long daysRented;

    @Column(nullable = true)
    private String returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int delayFee;

    @ManyToOne()
    @JoinColumn(name = "customer")
    private CustomerModel customer;
    
    @ManyToOne()
    @JoinColumn(name = "game")
    private GamesModel game;
    

}
