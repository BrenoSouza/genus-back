package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.genus.inputs.GetUsersFromInstitutionByRoleInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.services.InstitutionService;

public class InstitutionResolver implements GraphQLResolver<Institution> {
    
    @Autowired
    private InstitutionService institutionService;

    public Iterable<Grade> getGrades(Institution institution) {
        return institution.getGrades();
    }
    
    public Iterable<User> getAdmins(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.ADMIN);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
    
    public Iterable<User> getTeachers(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.TEACHER);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
    
    public Iterable<User> getStudents(Institution institution) {
    	GetUsersFromInstitutionByRoleInput input = new GetUsersFromInstitutionByRoleInput(institution.getId(), UserRole.STUDENT);
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
}