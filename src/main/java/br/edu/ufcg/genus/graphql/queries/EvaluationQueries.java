package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.services.EvaluationService;

public class EvaluationQueries implements GraphQLQueryResolver {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public Evaluation findEvaluation(Long id) {
		return evaluationService.findEvaluation(id);
	}
	

}
