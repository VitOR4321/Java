package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
