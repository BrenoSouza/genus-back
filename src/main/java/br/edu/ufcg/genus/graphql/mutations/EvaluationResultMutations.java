package br.edu.ufcg.genus.graphql.mutations;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.CreateEvaluationResultInput;
import br.edu.ufcg.genus.models.EvaluationResult;

import br.edu.ufcg.genus.services.EvaluationResultService;
import br.edu.ufcg.genus.services.UserService;

public class EvaluationResultMutations implements GraphQLMutationResolver {
	
	@Autowired
	public EvaluationResultService evaluationResultService;
	
	@Autowired
	public UserService userService;
	
	public List<EvaluationResult> createEvaluationResults(Collection<CreateEvaluationResultInput> inputs) {
		return this.evaluationResultService.createEvaluationResults(inputs, userService.findLoggedUser());
	}
	
	public EvaluationResult editEvaluationResult(Long resultId, Double newResult) {
		return this.evaluationResultService.editEvaluationResult(resultId, newResult, userService.findLoggedUser());
	}
	
	

}
