package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.EvaluationResult;
import br.edu.ufcg.genus.services.EvaluationResultService;

public class EvaluationResultQueries implements GraphQLQueryResolver {
	
	@Autowired
	public EvaluationResultService evaluationResultService;
	
	public EvaluationResult findEvaluationResult(Long id) {
		return evaluationResultService.findEvaluationResult(id);
	}

}
