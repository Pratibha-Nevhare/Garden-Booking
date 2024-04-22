package com.garden.dao;

import com.garden.entities.G_customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GCustomerRepository extends JpaRepository<G_customer, Integer> {
    
}

