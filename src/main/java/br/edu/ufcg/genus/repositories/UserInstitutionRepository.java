package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserInstitutionId;

public interface UserInstitutionRepository extends CrudRepository<UserInstitution, UserInstitutionId>{
	
	public Optional<UserInstitution> findById(UserInstitutionId id);

}
