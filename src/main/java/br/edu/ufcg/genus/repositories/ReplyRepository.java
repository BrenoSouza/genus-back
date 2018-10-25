package br.edu.ufcg.genus.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long> {
	
	public Optional<Reply> findById(Long id);
	
	public Page<Reply> findByDiscussionId(Pageable pageRequest, Long id);
	
}
