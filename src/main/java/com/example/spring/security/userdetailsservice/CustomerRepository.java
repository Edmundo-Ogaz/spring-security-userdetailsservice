package com.example.spring.security.userdetailsservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository< CustomerModel, Long > {
    CustomerModel findByEmail(String email);
}
