package br.edu.ufcg.genus.repositories;

import br.edu.ufcg.genus.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> { 

	public User findByEmail(String email);
	
	public User findByUsername(String username);

}
