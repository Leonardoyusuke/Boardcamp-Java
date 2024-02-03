package com.boardcamp.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boardcamp.api.models.RentalsModel;

public interface RentalsRepository extends JpaRepository<RentalsModel, Long>{
    public int countByGameIdAndReturnDateIsNull(Long gameId);

    @Query("UPDATE RentalsModel SET delayFee = :delayFee WHERE id = :id")
    public void updateDelayFee(Long id, int delayFee);
}
