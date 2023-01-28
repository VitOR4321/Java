package com.example.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Registration;
import com.example.repositories.RegistrationRepository;

@Service
public class RegistrationServices {

	@Autowired
	private RegistrationRepository repository;

	public RegistrationServices() {
		super();
	}
	
	public <S extends Registration> S save(S entity) {
		return repository.save(entity);
	}

	public List<Registration> findAll() {
		return repository.findAll();
	}

	public Optional<Registration> findById(Long id) {
		return repository.findById(id);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
