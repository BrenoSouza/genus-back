package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.services.SubjectService;;

public class GradeResolver implements GraphQLResolver<Grade> {

    @Autowired
    private SubjectService subjectService;

    public Iterable<Subject> getSubjects(Grade grade) {
        return subjectService.findSubjectsByGrade(grade.getId());
    }

    public Institution getInstitution(Grade grade) {
        return grade.getInstitution();
    }
}