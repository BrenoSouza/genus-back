package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.genus.inputs.GetUsersFromInstitutionByRoleInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.services.GradeService;
import br.edu.ufcg.genus.services.InstitutionService;

public class InstitutionResolver implements GraphQLResolver<Institution> {

    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private InstitutionService institutionService;

    public Iterable<Grade> getGrades(Institution institution) {
        return gradeService.findGradesByInstitution(institution.getId());
    }
    
    public Iterable<User> getAdminList(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.ADMIN);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
    
    public Iterable<User> getTeacherList(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.TEACHER);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
    
    public Iterable<User> getStudentList(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.STUDENT);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
}