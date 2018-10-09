package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.AttributeDoNotMatchException;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.exception.InvalidTokenException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.inputs.CreateInstitutionInput;
import br.edu.ufcg.genus.inputs.GetUsersFromInstitutionByRoleInput;
import br.edu.ufcg.genus.inputs.RemoveUserFromInstitutionInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.repositories.UserInstitutionRepository;

@Service
public class InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserInstitutionRepository userInstitutionRepository;
	
    public Optional<Institution> findById(Long id) {
        return institutionRepository.findById(id);
    }

    public Institution createInstitution(CreateInstitutionInput input) {
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userService.findUserByEmail(email)
        .orElseThrow(() -> new InvalidTokenException("Token inválido."));

        Institution institution = new Institution();
        institution.setName(input.getName());
        institution.setEmail(input.getEmail());
        institution.setPhone(input.getPhone());
        institution.setAddress(input.getAddress());
        institutionRepository.save(institution);
        addUserToInstitution(owner, institution, UserRole.ADMIN); // calls save
        return institution;
    }

	public void addGradeToInstitution(Institution institution, Grade newGrade) {
		institution.addGrade(newGrade);
		institutionRepository.save(institution);
	}
	
	public List<Institution> getInstitutionsFromLoggedUser() {
		User user = this.userService.findLoggedUser();
		return user.findInstitutions();
	}
	
	public void addUserToInstitution(User user, Institution institution, UserRole role) {
		UserInstitution userInstitution = institution.addUser(user, role);
		this.userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		this.userService.saveUserInRepository(user);
	}
	
	public List<User> getAllUsersFromInstitution(Long institutionId) {
		Institution institution = findById(institutionId).orElseThrow(() -> new InvalidIDException());
		List<User> result = new ArrayList<>();
		for (UserInstitution userInstitution : institution.getUsers()) {
			if (userInstitution.getInstitution().equals(institution)) {
				result.add(userInstitution.getUser());
			}
		}
		return result;
	}
	
	public List<User> getUsersFromInstitutionByRole(GetUsersFromInstitutionByRoleInput input) {
		Institution institution = findById(input.getInstitutionId()).orElseThrow(() -> new InvalidIDException());
		List<User> result = new ArrayList<>();
		for (UserInstitution userInstitution : institution.getUsers()) {
			if (userInstitution.getInstitution().equals(institution) && userInstitution.getRole().equals(input.getRole())) {
				result.add(userInstitution.getUser());
			}
		}
		return result;
	}
	
	public boolean removeUserFromInstitution (RemoveUserFromInstitutionInput input) {
		User user = this.userService.findLoggedUser();
		if (!this.userService.passwordMatch(user, input.getPassword())) {
			throw new AttributeDoNotMatchException("Senha invalida.");
		}
		Institution institution = findById(input.getInstitutionId()).orElseThrow(() -> new InvalidIDException());
		if (!institution.getName().equals(input.getInstitutionName())) {
			throw new AttributeDoNotMatchException("Nome de instituicao invalido");
		}
		
		User toBeRemoved = userService.findUserById(input.getToBeRemovedId()).orElseThrow(() -> new InvalidIDException());
		
		boolean result;
		if (user.equals(toBeRemoved)) {
			result = removeSelfFromInstitution(user, institution);
		} else {
			result = removeOtherFromInstitution(user, institution, toBeRemoved);
		}
		return result;
	}

	private boolean removeSelfFromInstitution(User user, Institution institution) {
		boolean result = institution.removeUser(user);
		this.userService.saveUserInRepository(user);
		if (institution.getUsers().isEmpty()) {
			this.institutionRepository.delete(institution);
		} else {
			this.institutionRepository.save(institution);
		}
		return result;
	}
	
	private boolean removeOtherFromInstitution(User user, Institution institution, User toBeRemoved) {
		boolean result = false;
		List<UserRole> permittedRoles = new ArrayList<>();
		permittedRoles.add(UserRole.ADMIN);
		if(!user.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new InvalidPermissionException(permittedRoles);
		if(toBeRemoved.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new RuntimeException("Nao pode remover ADMIN");
		//result = institution.removeUser(toBeRemoved);
		for(Iterator<UserInstitution> iterator = institution.getUsers().iterator(); iterator.hasNext();) {
			UserInstitution userInstitution = iterator.next();
			if (userInstitution.getUser().equals(toBeRemoved) && userInstitution.getInstitution().equals(institution)) {
				System.out.println("Entrou no if");
				System.out.println("size users: " + userInstitution.getInstitution().getUsers().size());
				System.out.println("size institutions: " + userInstitution.getUser().getInstitutions().size());
				iterator.remove();
				System.out.println(toBeRemoved.getInstitutions().toString());
				userInstitution.getUser().getInstitutions().remove(userInstitution);
				result = toBeRemoved.getInstitutions().remove(userInstitution);
				//this.userInstitutionRepository.delete(userInstitution);
				this.userInstitutionRepository.deleteById(userInstitution.getId());
				System.out.println("Removeu");
				System.out.println("size users: " + userInstitution.getInstitution().getUsers().size());
				System.out.println("size institutions: " + userInstitution.getUser().getInstitutions().size());
				userInstitution.setInstitution(null);
				userInstitution.setUser(null);
			}
		}
		//aaa
		System.out.println("Removeu 2");
		System.out.println("size users: " + institution.getUsers().size());
		System.out.println("size institutions: " + toBeRemoved.getInstitutions().size());
		//this.institutionRepository.save(institution);
		System.out.println("Removeu 4");
		System.out.println("size users: " + institution.getUsers().size());
		System.out.println("size institutions: " + toBeRemoved.getInstitutions().size());
		//this.userService.saveUserInRepository(toBeRemoved);
		System.out.println("Removeu 3");
		System.out.println("size users: " + institution.getUsers().size());
		System.out.println("size institutions: " + toBeRemoved.getInstitutions().size());
		return result;
		
		
	}

	
}