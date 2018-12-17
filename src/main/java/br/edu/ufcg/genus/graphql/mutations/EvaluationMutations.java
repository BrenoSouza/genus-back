package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.services.EvaluationService;
import br.edu.ufcg.genus.services.UserService;

public class EvaluationMutations implements GraphQLMutationResolver {
	
	@Autowired
	private EvaluationService evaluationService;
	@Autowired
	private UserService userService;
	
	public Evaluation createEvaluation(EvaluationCreationInput input) {
		return evaluationService.createEvaluation(input, userService.findLoggedUser());
	}

}
