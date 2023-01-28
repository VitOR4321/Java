package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Ranking;
import com.example.repositories.RankingRepository;

@Service
public class RankingService {

	@Autowired
	private RankingRepository repository;

	public RankingService() {
		super();
	}

	public <S extends Ranking> S save(S entity) {
		return repository.save(entity);
	}

	public List<Ranking> findAll() {
		return repository.findAll();
	}

	public Optional<Ranking> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
}
