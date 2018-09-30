package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.EntryCode;

public interface EntryCodeRepository extends CrudRepository<EntryCode, String>{
	
	public Optional<EntryCode> findByCode(String code);
	
	@Transactional
	public String deleteByCode(String code);

}
