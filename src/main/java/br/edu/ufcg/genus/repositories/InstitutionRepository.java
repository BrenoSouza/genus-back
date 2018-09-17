package br.edu.ufcg.genus.repositories;

import br.edu.ufcg.genus.models.Institution;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface InstitutionRepository extends CrudRepository<Institution, Long> { 
	
	public Optional<Institution> findByEmail(String email);

	public Optional<Institution> findById(int id);

}
