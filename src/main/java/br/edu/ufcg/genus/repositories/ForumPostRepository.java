package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.ForumPost;

public interface ForumPostRepository extends CrudRepository<ForumPost, Long>{
	
	public Optional<ForumPost> findById(Long id);

}
