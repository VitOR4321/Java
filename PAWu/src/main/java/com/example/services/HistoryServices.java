package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.History;
import com.example.repositories.HistoryRepository;

@Service
public class HistoryServices {

	@Autowired
	private HistoryRepository repository;

	public HistoryServices() {
		super();
	}

	public <S extends History> S save(S entity) {
		return repository.save(entity);
	}

	public List<History> findAll() {
		return repository.findAll();
	}

	public Optional<History> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
