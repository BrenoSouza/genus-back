package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.EvaluationResult;

public interface EvaluationResultRepository extends CrudRepository<EvaluationResult, Long>{
	
	public Optional<EvaluationResult> findById(Long id);

}
