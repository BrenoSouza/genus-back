package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.services.GradeService;

public class GradeMutations implements GraphQLMutationResolver {
	
	@Autowired
	private GradeService gradeService;
	
	public Grade createGrade(GradeCreationInput input) {
		return gradeService.createGrade(input);
	}

}
