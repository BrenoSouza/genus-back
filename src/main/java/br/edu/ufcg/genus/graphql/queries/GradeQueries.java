package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.services.GradeService;

public class GradeQueries implements GraphQLQueryResolver {
	
	@Autowired
	private GradeService gradeService;
	
	public Grade findGrade(long id) {
		return this.gradeService.findGradeById(id).orElseThrow(() -> new InvalidIDException());
	}
}
