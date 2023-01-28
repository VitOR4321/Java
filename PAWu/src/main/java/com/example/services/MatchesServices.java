package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Matches;
import com.example.repositories.MatchesRepository;

@Service
public class MatchesServices {
	
	@Autowired
	private MatchesRepository repository;

	public MatchesServices() {
		super();
	}

	public <S extends Matches> S save(S entity) {
		return repository.save(entity);
	}

	public List<Matches> findAll() {
		return repository.findAll();
	}

	public Optional<Matches> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
