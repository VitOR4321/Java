package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Matches;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Long> {

}
