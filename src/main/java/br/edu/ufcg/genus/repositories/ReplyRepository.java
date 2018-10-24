package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long> {
	
	public Optional<Reply> findById(Long id);
	
}
