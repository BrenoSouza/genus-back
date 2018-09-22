package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long>{
	
	public Optional<Subject> findById(Long id);

}
