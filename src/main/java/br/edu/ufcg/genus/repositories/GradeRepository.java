package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Grade;

public interface GradeRepository extends CrudRepository<Grade, Long>{
	
	public Optional<Grade> findById(Long id);
	
    public Iterable<Grade> findByInstitutionId(Long institutionId);

}
