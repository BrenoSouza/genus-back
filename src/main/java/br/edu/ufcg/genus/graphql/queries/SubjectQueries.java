package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.services.SubjectService;

public class SubjectQueries implements GraphQLQueryResolver {
	
	@Autowired
	private SubjectService subjectService;
	
	public Subject findSubject(long id) {
		return this.subjectService.findSubjectById(id).orElseThrow(() -> new InvalidIDException());
	}

}
