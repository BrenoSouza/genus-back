package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Evaluation;

public interface EvaluationRepository extends CrudRepository<Evaluation, Long>{
	
	public Optional<Evaluation> findById(Long id);

}
