package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.ExpiredEntryCodeException;
import br.edu.ufcg.genus.exception.InvalidEntryCodeException;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.UserAlreadyInInstitutionException;
import br.edu.ufcg.genus.inputs.CreateEntryCodeInput;
import br.edu.ufcg.genus.models.EntryCode;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.EntryCodeRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;
import br.edu.ufcg.genus.utils.ServerConstants;

@Service
public class EntryCodeService {
	
	@Autowired
	private EntryCodeRepository entryCodeRepository;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private UserService userService;
	
	public Optional<EntryCode> findEntryCode(String code) {
		return this.entryCodeRepository.findByCode(code);				
	}
	
	public String createEntryCode(CreateEntryCodeInput input) {
		User user = this.userService.findLoggedUser();
		ArrayList<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, input.getInstitutionId(), permitedRoles);
		EntryCode entryCode = new EntryCode(generateRandomCode(), input.getRole(), input.getInstitutionId());
		this.entryCodeRepository.save(entryCode);
		return entryCode.getCode();
	}
	
	public Institution joinInstitution(String code) {
		EntryCode entryCode = findEntryCode(code).orElseThrow(() -> new InvalidEntryCodeException());
		
		//Checks expiration
		Date now = new Date();
		if (now.after(entryCode.getExpirationDate())) {
			this.entryCodeRepository.delete(entryCode);
			throw new ExpiredEntryCodeException();
		}
		
		//Checks if this User is already in the institution
		User user = this.userService.findLoggedUser();
		if (user.getRole(entryCode.getInstitutionId()) != null) {
			throw new UserAlreadyInInstitutionException();
		}
		
		Institution institution = this.institutionService.findById(entryCode.getInstitutionId()).orElseThrow(() -> new InvalidIDException());
		this.institutionService.addUserToInstitution(user, institution, entryCode.getRole());
		this.entryCodeRepository.delete(entryCode);
		return institution;
		
	}
	
	
	private String generateRandomCode() {
		String generatedString = RandomStringUtils.randomAlphanumeric(ServerConstants.ENTRY_CODE_SIZE);
		//checks if this code is already being used
		EntryCode aux = findEntryCode(generatedString).orElse(null);
		while (aux != null) {
			Date now = new Date();
			if (now.after(aux.getExpirationDate())) {
				this.entryCodeRepository.delete(aux);
				aux = null;
			} else {
				generatedString = RandomStringUtils.randomAlphanumeric(ServerConstants.ENTRY_CODE_SIZE);
			}
		}
		return generatedString;
	}

}
