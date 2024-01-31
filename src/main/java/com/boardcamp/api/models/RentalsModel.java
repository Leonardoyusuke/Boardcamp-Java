package com.boardcamp.api.models;

import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private String daysRented;

    @Column(nullable = true)
    private String returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int delayFee;
    
    @ManyToOne
    // @JoinColumn(name = "game")
    // private GamesModel game;
    @JoinColumn(name = "customer")
    private CustomerModel customer;


    
}
