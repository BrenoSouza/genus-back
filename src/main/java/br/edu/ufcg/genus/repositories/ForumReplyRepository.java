package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.ForumReply;

public interface ForumReplyRepository extends CrudRepository<ForumReply, Long> {
	
	public Optional<ForumReply> findById(Long id);
	
}
