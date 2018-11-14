package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.services.StudentSubjectService;

public class StudentSubjectQueries implements GraphQLQueryResolver {
	
	@Autowired
	private StudentSubjectService studentSubjectService;
	
	public StudentSubject findStudentSubject(Long userId, Long subjectId) {
		return this.studentSubjectService.findStudentSubject(userId, subjectId);
	}
}
