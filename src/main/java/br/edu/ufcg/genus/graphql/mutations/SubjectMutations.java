package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.beans.SubjectCreationInput;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.services.SubjectService;

public class SubjectMutations implements GraphQLMutationResolver {
	
	@Autowired
	private SubjectService subjectService;
	
	public Subject createSubject(SubjectCreationInput input) {
		return this.subjectService.createSubject(input);		
	}

}
