package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Discussion;

public interface DiscussionRepository extends CrudRepository<Discussion, Long>{
	
	public Optional<Discussion> findById(Long id);

}
