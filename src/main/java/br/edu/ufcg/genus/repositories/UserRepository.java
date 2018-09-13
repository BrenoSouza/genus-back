package br.edu.ufcg.genus.repositories;

import br.edu.ufcg.genus.models.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> { 
	
	public Optional<User> findByUsername(String username);

}
