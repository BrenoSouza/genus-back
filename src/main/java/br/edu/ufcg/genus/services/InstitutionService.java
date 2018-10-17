package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.InvalidCredentialsException;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;

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
import br.edu.ufcg.genus.models.Subject;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userService.findUserByEmail(email)
        .orElseThrow(() -> new InvalidTokenException("Token is not valid."));

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
		Institution institution = findById(institutionId).orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", institutionId));
		List<User> result = new ArrayList<>();
		for (UserInstitution userInstitution : institution.getUsers()) {
			if (userInstitution.getInstitution().equals(institution)) {
				result.add(userInstitution.getUser());
			}
		}
		return result;
	}
	
	public List<User> getUsersFromInstitutionByRole(GetUsersFromInstitutionByRoleInput input) {
		Institution institution = findById(input.getInstitutionId()).orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", input.getInstitutionId()));
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
			throw new InvalidCredentialsException("Invalid email or password", null);

		}
		Institution institution = findById(input.getInstitutionId()).orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", input.getInstitutionId()));
		if (!institution.getName().equals(input.getInstitutionName())) {
            throw new NotAuthorizedException("You don't have permission to do this");
		}
		
		User toBeRemoved = userService.findUserById(input.getToBeRemovedId()).orElseThrow(() -> new InvalidIDException("User with passed ID was not found", input.getToBeRemovedId()));
		
		boolean result = false;
		if (user.equals(toBeRemoved)) {
			result = removeSelfFromInstitution(user, institution);
		} else {
			result = removeOtherFromInstitution(user, institution, toBeRemoved);
		}
		return result;
	}
	
	private boolean removeSelfFromInstitution(User user, Institution institution) {
		boolean result = removeUserInstitution(user, institution);
		if (institution.getUsers().isEmpty()) {
			this.institutionRepository.deleteById(institution.getId());
		}
		return result;
	}
	
	private boolean removeOtherFromInstitution(User user, Institution institution, User toBeRemoved) {
		List<UserRole> permittedRoles = new ArrayList<>();
		permittedRoles.add(UserRole.ADMIN);
		if(!user.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new InvalidPermissionException(permittedRoles);
		if(toBeRemoved.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new RuntimeException("Cannot remove ADMIN");
		return removeUserInstitution(toBeRemoved, institution);		
	}
	
	private boolean removeUserInstitution (User user, Institution institution) {
		boolean result = false;
		removeSubjectsFromUser(user, institution);
		for(Iterator<UserInstitution> iterator = institution.getUsers().iterator(); iterator.hasNext();) {
			UserInstitution userInstitution = iterator.next();
			if (userInstitution.getUser().equals(user) && userInstitution.getInstitution().equals(institution)) {
				iterator.remove();
				userInstitution.getUser().getInstitutions().remove(userInstitution);
				result = user.getInstitutions().remove(userInstitution);
				this.userInstitutionRepository.deleteById(userInstitution.getId());
				userInstitution.setInstitution(null);
				userInstitution.setUser(null);
			}
		}
		return result;
	}
	
	private void removeSubjectsFromUser(User user, Institution institution) {
		for (Subject subject : user.getSubjects()) {
			if (subject.getGrade().getInstitution().equals(institution)) {
				user.removeSubject(subject);
			}
		}
		this.userService.saveUserInRepository(user);
	}
}