package com.boardcamp.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.CustomerModel;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long>{
    boolean existsBycpf(String cpf);

    //@Query(value = "SELECT FROM public.customers WHERE id = :id", nativeQuery = true)
    // Optional<CustomerModel> findById(@Param("id") Long id);
}
