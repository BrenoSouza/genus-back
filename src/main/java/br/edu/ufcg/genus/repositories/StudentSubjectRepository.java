package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.StudentSubjectId;

public interface StudentSubjectRepository extends CrudRepository<StudentSubject, StudentSubjectId>{
	
	public Optional<StudentSubject> findById(StudentSubjectId id);

}
