package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.SubjectService;

public class SubjectQueries implements GraphQLQueryResolver {
	
	@Autowired
	private SubjectService subjectService;
	
	public Subject findSubjectById(long subjectId) {
		return this.subjectService.findSubjectById(subjectId);
	}

	public Iterable<Subject> findSubjectsByGrade(Long gradeId) {
        return subjectService.findSubjectsByGrade(gradeId);
	}
	
	public Iterable<User> findTeachersBySubject(Long subjectId) {
		return this.subjectService.findTeachersBySubject(subjectId);
	}
}
