package com.boardcamp.api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerCPFConflictExpections;
import com.boardcamp.api.exceptions.UserNotFoundExpections;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {

    final CustomerRepository customerRepository;
    
    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerDTO dto){
        if(customerRepository.existsBycpf(dto.getCpf())){
            throw new CustomerCPFConflictExpections("CPF already exists");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }

    public Optional<CustomerModel> findById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new UserNotFoundExpections("User not found");
        }
        return customerRepository.findById(id);
    }
}
