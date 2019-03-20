package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.InvalidCredentialsException;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import br.edu.ufcg.genus.update_inputs.UpdateInstitutionInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private UserInstitutionRepository userInstitutionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
    public Institution findById(Long id) {
        return institutionRepository.findById(id)
        		.orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", id));
    }

    public Institution createInstitution(CreateInstitutionInput input, User owner) {
        Institution institution = new Institution(input.getName(), input.getAddress(), input.getPhone(), input.getEmail());
        institutionRepository.save(institution);
        addUserToInstitution(owner, institution, UserRole.ADMIN); // calls save
        return institution;
    }

	public void addGradeToInstitution(Institution institution, Grade newGrade) {
		institution.addGrade(newGrade);
		institutionRepository.save(institution);
	}
	
	public List<Institution> getInstitutionsFromUser(User user) {
		return user.findInstitutions();
	}
	
	public void addUserToInstitution(User user, Institution institution, UserRole role) {
		UserInstitution userInstitution = institution.addUser(user, role);
		this.userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		this.userService.saveUserInRepository(user);
	}
	
	public List<User> getAllUsersFromInstitution(Long institutionId) {
		Institution institution = findById(institutionId);
		List<User> result = new ArrayList<>();
		for (UserInstitution userInstitution : institution.getUsers()) {
			if (userInstitution.getInstitution().equals(institution)) {
				result.add(userInstitution.getUser());
			}
		}
		return result;
	}
	
	public List<User> getUsersFromInstitutionByRole(GetUsersFromInstitutionByRoleInput input) {
		Institution institution = findById(input.getInstitutionId());
		List<User> result = new ArrayList<>();
		for (UserInstitution userInstitution : institution.getUsers()) {
			if (userInstitution.getInstitution().equals(institution) && userInstitution.getRole().equals(input.getRole())) {
				result.add(userInstitution.getUser());
			}
		}
		return result;
	}
	
	public boolean removeUserFromInstitution (RemoveUserFromInstitutionInput input, User user) {
		if (!this.userService.passwordMatch(user, input.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");

		}
		Institution institution = findById(input.getInstitutionId());
		if (!institution.getName().equals(input.getInstitutionName())) {
            throw new NotAuthorizedException("You don't have permission to do this");
		}
		
		User toBeRemoved = userService.findUserById(input.getToBeRemovedId());
		
		toBeRemoved.setLastInstitutionId(-1);
		userService.saveUserInRepository(toBeRemoved);
		
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
		if(!user.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new NotAuthorizedException();
		if(toBeRemoved.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new NotAuthorizedException();;
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
		User loggedUser = userService.findLoggedUser();
		this.subjectService.removeTeacherFromInstitutionSubjects(institution.getId(), user.getId(), loggedUser);
		this.subjectService.removeInstitutionSubjectsFromUser(institution.getId(), user.getId(), loggedUser);
	}

	public Institution updateInstitution(UpdateInstitutionInput input, User user) {
		List<UserRole> permittedRoles = new ArrayList<>();
		permittedRoles.add(UserRole.ADMIN);

		Institution institution = findById(input.getInstitutionId());
		PermissionChecker.checkPermission(user, institution.getId(), permittedRoles);
		
        if (input.getName() != null) {
            institution.setName(input.getName());
		}
		
		if (input.getAddress() != null) {
            institution.setAddress(input.getAddress());
		}
		
		if (input.getPhone() != null) {
            institution.setPhone(input.getPhone());
        }

        return institutionRepository.save(institution);
    }
}