package br.edu.ufcg.genus.repositories;

import br.edu.ufcg.genus.models.Institution;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InstitutionRepository extends CrudRepository<Institution, Long> { 
	
	public Iterable<Institution> findByEmail(String email);

	public Optional<Institution> findById(int institutionId);


}
